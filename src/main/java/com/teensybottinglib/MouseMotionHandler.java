package com.teensybottinglib;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;
import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;
import com.github.joonasvali.naturalmouse.api.SystemCalls;
import com.github.joonasvali.naturalmouse.support.*;

import java.awt.*;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class MouseMotionHandler
{
    private MouseMotionFactory generalLocationFactory;
    private MouseMotionFactory exactLocationFactory;

    private TeensyController teensyController;

    private MouseMotionFactory getGeneralLocationFactory()
    {
        MouseMotionFactory factory = new MouseMotionFactory(new DefaultMouseMotionNature());
//        List<Flow> flows = new ArrayList<>(Arrays.asList(
//                new Flow(FlowTemplates.variatingFlow()),
//                new Flow(FlowTemplates.slowStartup2Flow()),
//                new Flow(FlowTemplates.adjustingFlow()),
//                new Flow(FlowTemplates.jaggedFlow())
//        ));

        factory.setDeviationProvider(new SinusoidalDeviationProvider(SinusoidalDeviationProvider.DEFAULT_SLOPE_DIVIDER));
        factory.setNoiseProvider(new DefaultNoiseProvider(20));
        factory.getNature().setReactionTimeVariationMs(0);
        factory.getNature().setSystemCalls(new TeensySystemCalls(teensyController));
        factory.getNature().setMouseInfo(new TeensyMouseAccessor());

//        DefaultSpeedManager manager = new DefaultSpeedManager(flows);
        DefaultSpeedManager manager = new DefaultSpeedManager();
        manager.setMouseMovementBaseTimeMs(250);
        factory.setSpeedManager(manager);

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(0);


        return factory;
    }

    private MouseMotionFactory getExactLocationFactory()
    {
        MouseMotionFactory factory = getGeneralLocationFactory();
        factory.getNature().setReactionTimeVariationMs(100);
        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(2);
        return factory;
    }

    public MouseMotionHandler(TeensyController teensyController)
    {
        this.teensyController = teensyController;

        this.generalLocationFactory = getGeneralLocationFactory();
        this.exactLocationFactory = getExactLocationFactory();
    }

    public void mouseMoveGeneralLocation(int x, int y)
    {
        try {
            generalLocationFactory.move(x, y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void mouseMoveExactLocation(int x, int y)
    {
        try {
            exactLocationFactory.move(x, y);
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
    private TeensyController teensyController;

    public TeensySystemCalls(TeensyController teensyController)
    {
        super();
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

    @Override
    public void setMousePosition(int x, int y)
    {
        teensyController.mouseMove(x, y);
    }
}
