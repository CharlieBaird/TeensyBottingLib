package com.TeensyBottingLib.MouseFactories.MotionFactories;

import com.TeensyBottingLib.MouseFactories.AsyncMouseMotion;
import com.github.joonasvali.naturalmouse.api.MouseMotion;
import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;
import com.github.joonasvali.naturalmouse.support.*;
import com.github.joonasvali.naturalmouse.util.FlowTemplates;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GeneralMotionFactory extends MouseMotionFactory
{
    ArrayList<Flow> flows = new ArrayList<>(Arrays.asList(
            new Flow(FlowTemplates.variatingFlow()),
            new Flow(FlowTemplates.slowStartup2Flow()),
            new Flow(FlowTemplates.adjustingFlow()),
            new Flow(FlowTemplates.jaggedFlow())
    ));

    public GeneralMotionFactory()
    {
        super(new DefaultMouseMotionNature());

        setDeviationProvider(new SinusoidalDeviationProvider(SinusoidalDeviationProvider.DEFAULT_SLOPE_DIVIDER));
        setNoiseProvider(new DefaultNoiseProvider(20));
        getNature().setReactionTimeVariationMs(0);

        DefaultSpeedManager manager = new DefaultSpeedManager(flows);
        manager.setMouseMovementBaseTimeMs(350);
        setSpeedManager(manager);

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) getOvershootManager();
        overshootManager.setOvershoots(0);
    }

    @Override
    public AsyncMouseMotion build(int xDest, int yDest)
    {
        return new AsyncMouseMotion(getNature(), getRandom(), xDest, yDest);
    }
}
