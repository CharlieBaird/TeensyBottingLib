package com.charliebaird.MouseFactories;

import com.charliebaird.TeensyIO;

public abstract class GeneralTeensyMotionFactory extends GeneralMotionFactory
{
    protected final TeensyIO teensyIO;

    public GeneralTeensyMotionFactory(TeensyIO teensyIO)
    {
        super();
        this.teensyIO = teensyIO;
    }
}
