package com.TeensyBottingLib.MouseFactories.Support;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;

import java.awt.*;

public class TeensyRelativeMouseAccessor implements MouseInfoAccessor
{
    Point loc;

    public TeensyRelativeMouseAccessor()
    {
        loc = new Point(0, 0);
    }

    @Override
    public Point getMousePosition()
    {
        return loc;
    }

    public void setLoc(Point loc)
    {
        this.loc = loc;
    }
}
