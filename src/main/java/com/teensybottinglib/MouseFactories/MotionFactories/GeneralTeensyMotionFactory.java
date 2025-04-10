package com.TeensyBottingLib.MouseFactories.MotionFactories;

import com.TeensyBottingLib.TeensyIO;

public abstract class GeneralTeensyMotionFactory extends GeneralMotionFactory
{
    protected final TeensyIO teensyIO;

    public GeneralTeensyMotionFactory(TeensyIO teensyIO)
    {
        super();
        this.teensyIO = teensyIO;
    }
}
