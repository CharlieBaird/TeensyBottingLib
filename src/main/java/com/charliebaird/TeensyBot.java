package com.charliebaird;

import java.awt.*;

public class TeensyBot
{
    private final MouseMotionHandler mouseMotionHandler;
    private final TeensyController teensy;

    public TeensyBot()
    {
        teensy = new TeensyController();
        mouseMotionHandler = new MouseMotionHandler(teensy);

        // Make sure snippet runs to close connection to Teensy
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            teensy.close();
            System.out.println("Teensy connection closed");
        }));
    }

    public void delayMS(int ms)
    {
        int max = ms * 2;
        int value = (int) Math.floor(Math.random()*(max- ms +1)+ ms);

        try {
            Thread.sleep(value);
        } catch (InterruptedException ignored) {}
    }

    public void mouseMoveGeneralLocation(Point p)
    {
        mouseMotionHandler.mouseMoveGeneralLocation(p.x, p.y);
    }

    public void mouseMoveExactLocation(Point p)
    {
        mouseMotionHandler.mouseMoveExactLocation(p.x, p.y);
    }

    public void leftClick()
    {
        teensy.mousePress("left");
        delayMS(60);
        teensy.mouseRelease("left");
    }

    public void rightClick()
    {
        teensy.mousePress("right");
        delayMS(60);
        teensy.mouseRelease("right");
    }

    public void middleClick()
    {
        teensy.mousePress("middle");
        delayMS(60);
        teensy.mouseRelease("middle");
    }

    public void keyClick(String key)
    {
        teensy.keyPress(key);
        delayMS(60);
        teensy.keyRelease(key);
    }

    public void keyPress(KeyCode key)
    {
        teensy.keyPress(key.getSerialValue());
    }

    public void keyRelease(KeyCode key)
    {
        teensy.keyRelease(key.getSerialValue());
    }
}
