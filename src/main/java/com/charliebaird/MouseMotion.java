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

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class MouseMotion
{
    private MouseMotionFactory factory;
    private TeensyController teensyController;

    public MouseMotion(TeensyController teensyController)
    {
        this.teensyController = teensyController;

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
        factory.getNature().setMouseInfo(new TeensyMouseAccessor());
        manager.setMouseMovementBaseTimeMs(250);

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(0);

        factory.setSpeedManager(manager);

        this.factory = factory;
    }

    public void move(int x, int y)
    {
        try {
            factory.move(x, y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class TeensyMouseAccessor implements MouseInfoAccessor
{
    @Override
    public Point getMousePosition()
    {
        WinDef.POINT point = new WinDef.POINT();
        User32.INSTANCE.GetCursorPos(point);
        return new Point(point.x, point.y);
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

    static int i = 0;

    @Override
    public void setMousePosition(int x, int y)
    {
        System.out.println(i);
        if (i++ == 1000) System.exit(0);

        teensyController.mouseMove(x, y);
    }
}
