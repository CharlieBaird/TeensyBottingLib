package com.charliebaird;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;
import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;
import com.github.joonasvali.naturalmouse.api.SystemCalls;
import com.github.joonasvali.naturalmouse.support.*;
import com.github.joonasvali.naturalmouse.util.FlowTemplates;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MouseMotion
{
    private MouseMotionFactory factory;

    public MouseMotion(TeensyController teensyController)
    {
        MouseMotionFactory factory = new MouseMotionFactory(new DefaultMouseMotionNature());
        List<Flow> flows = new ArrayList<>(Arrays.asList(
                new Flow(FlowTemplates.variatingFlow()),
//                new Flow(FlowTemplates.slowStartupFlow()),
                new Flow(FlowTemplates.slowStartup2Flow()),
                new Flow(FlowTemplates.adjustingFlow()),
                new Flow(FlowTemplates.jaggedFlow())
        ));
        DefaultSpeedManager manager = new DefaultSpeedManager(flows);
        factory.setDeviationProvider(new SinusoidalDeviationProvider(SinusoidalDeviationProvider.DEFAULT_SLOPE_DIVIDER));
        factory.setNoiseProvider(new DefaultNoiseProvider(DefaultNoiseProvider.DEFAULT_NOISINESS_DIVIDER));
        factory.getNature().setReactionTimeVariationMs(0);
        factory.getNature().setSystemCalls(new TeensySystemCalls(this, teensyController));
        factory.getNature().setMouseInfo(new TeensyMouseAccessor(this));
        manager.setMouseMovementBaseTimeMs(250);

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(0);

        factory.setSpeedManager(manager);

        this.factory = factory;
    }

    public int curX;
    public int curY;

    public void move(int x, int y)
    {
        Point currentPos = MouseInfo.getPointerInfo().getLocation();

        curX = currentPos.x;
        curY = currentPos.y;

        try {
            factory.move(x, y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class TeensyMouseAccessor implements MouseInfoAccessor
{
    private MouseMotion mouseMotion;

    public TeensyMouseAccessor(MouseMotion mouseMotion)
    {
        this.mouseMotion = mouseMotion;
    }

    @Override
    public Point getMousePosition()
    {
        Point currentPos = MouseInfo.getPointerInfo().getLocation();
        return currentPos;
    }
}

class TeensySystemCalls implements SystemCalls
{
    MouseMotion mouseMotion;
    private TeensyController teensyController;

    public TeensySystemCalls(MouseMotion mouseMotion, TeensyController teensyController)
    {
        super();
        this.mouseMotion = mouseMotion;
        this.teensyController = teensyController;
    }

    @Override
    public long currentTimeMillis()
    {
        return System.currentTimeMillis();
    }

    @Override
    public void sleep(long l) throws InterruptedException
    {
        Thread.sleep(l);
    }

    @Override
    public Dimension getScreenSize()
    {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    int curX;
    int curY;
    public void initMovement()
    {

    }

    static int i = 0;

    @Override
    public void setMousePosition(int x, int y)
    {
        System.out.println(i);
        if (i++ == 100) System.exit(0);

        teensyController.mouseMove(x, y);

        System.out.println("(" + x + ", " + y + ")");
//
//        Point currentPos = new Point(mouseMotion.curX, mouseMotion.curY);
//        System.out.println("[DEBUG] Current position: (" + currentPos.x + ", " + currentPos.y + ")");
//
//        int dx = x - currentPos.x;
//        int dy = y - currentPos.y;
//
//        mouseMotion.curX += dx;
//        mouseMotion.curY += dy;
//
//        System.out.println("[DEBUG] Calculated delta: dx = " + dx + ", dy = " + dy);
//
//        System.out.println("[DEBUG] Sending command: MOUSE MOVE " + dx + " " + dy);
//
//        teensyController.mouseMove(dx, dy);
//
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.err.println("[ERROR] Sleep interrupted");
            throw new RuntimeException(e);
        }
    }
}
