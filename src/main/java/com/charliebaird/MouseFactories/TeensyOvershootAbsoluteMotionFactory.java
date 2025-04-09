package com.charliebaird.MouseFactories;

import com.charliebaird.MouseFactories.Support.TeensyAbsoluteMouseAccessor;
import com.charliebaird.MouseFactories.Support.TeensyAbsoluteSystemCalls;
import com.charliebaird.TeensyIO;
import com.github.joonasvali.naturalmouse.support.DefaultOvershootManager;

public class TeensyOvershootAbsoluteMotionFactory extends GeneralTeensyMotionFactory
{
    public TeensyOvershootAbsoluteMotionFactory(TeensyIO controller)
    {
        super(controller);
        getNature().setSystemCalls(new TeensyAbsoluteSystemCalls(teensyIO));
        getNature().setMouseInfo(new TeensyAbsoluteMouseAccessor());

        getNature().setReactionTimeVariationMs(100);
        DefaultOvershootManager overshootManager = (DefaultOvershootManager) getOvershootManager();
        overshootManager.setOvershoots(2);
    }
}
