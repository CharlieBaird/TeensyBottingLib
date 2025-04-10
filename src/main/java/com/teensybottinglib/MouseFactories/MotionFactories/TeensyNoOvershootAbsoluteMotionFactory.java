package com.TeensyBottingLib.MouseFactories.MotionFactories;

import com.TeensyBottingLib.MouseFactories.MotionFactories.Support.TeensyAbsoluteMouseAccessor;
import com.TeensyBottingLib.MouseFactories.MotionFactories.Support.TeensyAbsoluteSystemCalls;
import com.TeensyBottingLib.TeensyIO;
import com.github.joonasvali.naturalmouse.support.DefaultOvershootManager;

public class TeensyNoOvershootAbsoluteMotionFactory extends GeneralTeensyMotionFactory
{
    public TeensyNoOvershootAbsoluteMotionFactory(TeensyIO controller)
    {
        super(controller);
        getNature().setSystemCalls(new TeensyAbsoluteSystemCalls(teensyIO));
        getNature().setMouseInfo(new TeensyAbsoluteMouseAccessor());

        getNature().setReactionTimeVariationMs(0);
        DefaultOvershootManager overshootManager = (DefaultOvershootManager) getOvershootManager();
        overshootManager.setOvershoots(0);
    }
}
