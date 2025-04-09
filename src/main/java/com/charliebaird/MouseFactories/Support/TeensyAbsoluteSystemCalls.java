package com.charliebaird.MouseFactories.Support;

import com.charliebaird.TeensyIO;

public class TeensyAbsoluteSystemCalls extends SystemCallsParent
{
    private final TeensyIO teensyIO;

    public TeensyAbsoluteSystemCalls(TeensyIO teensyIO)
    {
        super();
        this.teensyIO = teensyIO;
    }

    @Override
    public void setMousePosition(int x, int y)
    {
        teensyIO.mouseMove(x, y);
    }
}
