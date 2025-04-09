package com.TeensyBottingLib.MouseFactories.Support;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;

public class TeensyAbsoluteMouseAccessor implements MouseInfoAccessor
{
    @Override
    public Point getMousePosition()
    {
        WinDef.POINT point = new WinDef.POINT();
        User32.INSTANCE.GetCursorPos(point);
        return new Point(point.x, point.y);
    }
}
