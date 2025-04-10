package com.TeensyBottingLib.MouseFactories;

import com.github.joonasvali.naturalmouse.api.MouseMotion;
import com.github.joonasvali.naturalmouse.api.MouseMotionObserver;
import com.github.joonasvali.naturalmouse.support.DoublePoint;
import com.github.joonasvali.naturalmouse.support.Flow;
import com.github.joonasvali.naturalmouse.support.MouseMotionNature;
import com.github.joonasvali.naturalmouse.support.mousemotion.Movement;
import com.github.joonasvali.naturalmouse.support.mousemotion.MovementFactory;
import com.github.joonasvali.naturalmouse.util.MathUtil;

import java.util.ArrayDeque;
import java.util.Random;

public class AsyncMouseMotion extends MouseMotion
{
    private static boolean movementCanceled = false;
    private static boolean currentlyMoving = false;

    public AsyncMouseMotion(MouseMotionNature nature, Random random, int xDest, int yDest)
    {
        super(nature, random, xDest, yDest);
    }

    public static void abortMovement()
    {
        if (!currentlyMoving) return;

        movementCanceled = true;

        // Block thread until not currently moving
        while (currentlyMoving) {
            System.out.println("Waiting for movement to abort");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Movement aborted");

        movementCanceled = false;
    }

    @Override
    public void move(MouseMotionObserver observer) throws InterruptedException
    {
        // Blocker boolean
        currentlyMoving = true;

        updateMouseInfo();

        MovementFactory movementFactory = new MovementFactory(xDest, yDest, speedManager, overshootManager, screenSize);
        ArrayDeque<Movement> movements = movementFactory.createMovements(mousePosition);
        int overshoots = movements.size() - 1;
        while (!movementCanceled && (mousePosition.x != xDest || mousePosition.y != yDest)) {
            System.out.println("Here");

            if (movements.isEmpty()) {
                // This shouldn't usually happen, but it's possible that somehow we won't end up on the target,
                // Then just re-attempt from mouse new position. (There are known JDK bugs, that can cause sending the cursor
                // to wrong pixel)
                updateMouseInfo();
                movements = movementFactory.createMovements(mousePosition);
            }

            Movement movement = movements.removeFirst();

            double distance = movement.distance;
            long mouseMovementMs = movement.time;
            Flow flow = movement.flow;
            double xDistance = movement.xDistance;
            double yDistance = movement.yDistance;

      /* Number of steps is calculated from the movement time and limited by minimal amount of steps
         (should have at least MIN_STEPS) and distance (shouldn't have more steps than pixels travelled) */
            int steps = (int) Math.ceil(Math.min(distance, Math.max(mouseMovementMs / timeToStepsDivider, minSteps)));

            long startTime = systemCalls.currentTimeMillis();
            long stepTime = (long) (mouseMovementMs / (double) steps);

            updateMouseInfo();
            double simulatedMouseX = mousePosition.x;
            double simulatedMouseY = mousePosition.y;

            double deviationMultiplierX = (random.nextDouble() - 0.5) * 2;
            double deviationMultiplierY = (random.nextDouble() - 0.5) * 2;

            double completedXDistance = 0;
            double completedYDistance = 0;
            double noiseX = 0;
            double noiseY = 0;

            for (int i = 0; i < steps; i++) {

                System.out.println("Stepping");
                if (movementCanceled) {
                    break;
                }

                // All steps take equal amount of time. This is a value from 0...1 describing how far along the process is.
                double timeCompletion = i / (double) steps;

                double effectFadeStep = Math.max(i - (steps - effectFadeSteps) + 1, 0);
                // value from 0 to 1, when effectFadeSteps remaining steps, starts to decrease to 0 linearly
                // This is here so noise and deviation wouldn't add offset to mouse final position, when we need accuracy.
                double effectFadeMultiplier = (effectFadeSteps - effectFadeStep) / effectFadeSteps;

                double xStepSize = flow.getStepSize(xDistance, steps, timeCompletion);
                double yStepSize = flow.getStepSize(yDistance, steps, timeCompletion);

                completedXDistance += xStepSize;
                completedYDistance += yStepSize;
                double completedDistance = Math.hypot(completedXDistance, completedYDistance);
                double completion = Math.min(1, completedDistance / distance);

                DoublePoint noise = noiseProvider.getNoise(random, xStepSize, yStepSize);
                DoublePoint deviation = deviationProvider.getDeviation(distance, completion);

                noiseX += noise.getX();
                noiseY += noise.getY();
                simulatedMouseX += xStepSize;
                simulatedMouseY += yStepSize;

                long endTime = startTime + stepTime * (i + 1);
                int mousePosX = MathUtil.roundTowards(
                        simulatedMouseX +
                                deviation.getX() * deviationMultiplierX * effectFadeMultiplier +
                                noiseX * effectFadeMultiplier,
                        movement.destX
                );

                int mousePosY = MathUtil.roundTowards(
                        simulatedMouseY +
                                deviation.getY() * deviationMultiplierY * effectFadeMultiplier +
                                noiseY * effectFadeMultiplier,
                        movement.destY
                );

                mousePosX = limitByScreenWidth(mousePosX);
                mousePosY = limitByScreenHeight(mousePosY);

                systemCalls.setMousePosition(
                        mousePosX,
                        mousePosY
                );

                // Allow other action to take place or just observe, we'll later compensate by sleeping less.
                observer.observe(mousePosX, mousePosY);

                long timeLeft = endTime - systemCalls.currentTimeMillis();
                sleepAround(Math.max(timeLeft, 0), 0);
            }
            updateMouseInfo();

            if (!movementCanceled)
            {
                if (mousePosition.x != movement.destX || mousePosition.y != movement.destY) {
                    // It's possible that mouse is manually moved or for some other reason.
                    // Let's start next step from pre-calculated location to prevent errors from accumulating.
                    // But print warning as this is not expected behavior.
                    systemCalls.setMousePosition(movement.destX, movement.destY);
                    // Let's wait a bit before getting mouse info.
                    sleepAround(SLEEP_AFTER_ADJUSTMENT_MS, 0);
                    updateMouseInfo();
                }

                if (mousePosition.x != xDest || mousePosition.y != yDest) {
                    // We are dealing with overshoot, let's sleep a bit to simulate human reaction time.
                    sleepAround(reactionTimeBaseMs, reactionTimeVariationMs);
                }
            }
        }

        currentlyMoving = false;
    }
}
