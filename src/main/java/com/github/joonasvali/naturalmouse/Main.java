package com.github.joonasvali.naturalmouse;

import com.github.joonasvali.naturalmouse.GeneralMotionFactory;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        GeneralMotionFactory factory = new GeneralMotionFactory();

        factory.move(500, 500);
    }
}
