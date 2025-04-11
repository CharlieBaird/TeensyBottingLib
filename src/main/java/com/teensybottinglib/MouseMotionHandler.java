package com.TeensyBottingLib;

import com.TeensyBottingLib.MouseFactories.AsyncMouseMotion;
import com.TeensyBottingLib.MouseFactories.MotionFactories.TeensyNoOvershootAbsoluteMotionFactory;
import com.TeensyBottingLib.MouseFactories.MotionFactories.TeensyNoOvershootRelativeMotionFactory;
import com.TeensyBottingLib.MouseFactories.MotionFactories.TeensyOvershootAbsoluteMotionFactory;

public class MouseMotionHandler
{
    private final TeensyNoOvershootAbsoluteMotionFactory generalLocationFactory;
    private final TeensyOvershootAbsoluteMotionFactory exactLocationFactory;
    private final TeensyNoOvershootRelativeMotionFactory relativeLocationFactory;

    public MouseMotionHandler(TeensyIO teensyIO)
    {
        this.generalLocationFactory = new TeensyNoOvershootAbsoluteMotionFactory(teensyIO);
        this.exactLocationFactory = new TeensyOvershootAbsoluteMotionFactory(teensyIO);
        this.relativeLocationFactory = new TeensyNoOvershootRelativeMotionFactory(teensyIO);
    }

    public void abortCurrentMovementIfExists()
    {
        AsyncMouseMotion.abortMovement();
    }

    public void mouseMoveGeneralLocation(int x, int y)
    {
        abortCurrentMovementIfExists();

        try {
            generalLocationFactory.move(x, y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void mouseMoveExactLocation(int x, int y)
    {
        abortCurrentMovementIfExists();

        try {
            exactLocationFactory.move(x, y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void mouseMoveRelative(int x, int y)
    {
        try {
            relativeLocationFactory.move(x, y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
