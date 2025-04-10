package com.TeensyBottingLib.MouseFactories;

import com.TeensyBottingLib.MouseFactories.Support.TeensyRelativeMouseAccessor;
import com.TeensyBottingLib.MouseFactories.Support.TeensyRelativeSystemCalls;
import com.TeensyBottingLib.TeensyIO;
import com.github.joonasvali.naturalmouse.support.DefaultOvershootManager;
import com.github.joonasvali.naturalmouse.support.DefaultSpeedManager;

public class TeensyNoOvershootRelativeMotionFactory extends GeneralTeensyMotionFactory
{
    public TeensyNoOvershootRelativeMotionFactory(TeensyIO teensyIO)
    {
        super(teensyIO);

        TeensyRelativeMouseAccessor teensyRelativeMouseAccessor = new TeensyRelativeMouseAccessor();
        getNature().setSystemCalls(new TeensyRelativeSystemCalls(teensyIO, teensyRelativeMouseAccessor));
        getNature().setMouseInfo(teensyRelativeMouseAccessor);

        getNature().setReactionTimeVariationMs(0);
        DefaultOvershootManager overshootManager = (DefaultOvershootManager) getOvershootManager();
        overshootManager.setOvershoots(0);

        DefaultSpeedManager manager = new DefaultSpeedManager(flows);
        manager.setMouseMovementBaseTimeMs(150);
        setSpeedManager(manager);
    }
}
