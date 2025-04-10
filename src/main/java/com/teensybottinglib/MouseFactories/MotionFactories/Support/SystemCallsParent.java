package com.TeensyBottingLib.MouseFactories.MotionFactories.Support;

import com.github.joonasvali.naturalmouse.api.SystemCalls;

import java.awt.*;

public abstract class SystemCallsParent implements SystemCalls
{
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
    public abstract void setMousePosition(int x, int y);
}
