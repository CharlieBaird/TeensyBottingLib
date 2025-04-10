package com.TeensyBottingLib;

import com.TeensyBottingLib.MouseFactories.MotionFactories.TeensyNoOvershootAbsoluteMotionFactory;
import com.TeensyBottingLib.MouseFactories.MotionFactories.TeensyNoOvershootRelativeMotionFactory;
import com.TeensyBottingLib.MouseFactories.MotionFactories.TeensyOvershootAbsoluteMotionFactory;
import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;

public class MouseMotionHandler
{
    private final MouseMotionFactory generalLocationFactory;
    private final MouseMotionFactory exactLocationFactory;
    private final MouseMotionFactory relativeLocationFactory;

    public MouseMotionHandler(TeensyIO teensyIO)
    {
        this.generalLocationFactory = new TeensyNoOvershootAbsoluteMotionFactory(teensyIO);
        this.exactLocationFactory = new TeensyOvershootAbsoluteMotionFactory(teensyIO);
        this.relativeLocationFactory = new TeensyNoOvershootRelativeMotionFactory(teensyIO);
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

    public void mouseMoveRelative(int x, int y)
    {
        try {
            relativeLocationFactory.move(x, y);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
