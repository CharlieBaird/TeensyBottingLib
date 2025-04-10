package com.TeensyBottingLib.MouseFactories;

import com.github.joonasvali.naturalmouse.api.MouseMotion;
import com.github.joonasvali.naturalmouse.support.MouseMotionNature;

import java.util.Random;

public class AsyncMouseMotion extends MouseMotion
{
    public AsyncMouseMotion(MouseMotionNature nature, Random random, int xDest, int yDest)
    {
        super(nature, random, xDest, yDest);
    }
}
