package com.charliebaird;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class SmartBot extends Robot
{
    private MouseMotion motion;
    private TeensyController teensyController;

    public static SmartBot getDefault(int screen)
    {
        SmartBot robot;

        try {
            robot = new SmartBot(screen);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        return robot;
    }

    public SmartBot(int screen) throws AWTException
    {
        this(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[screen]);
    }

    public SmartBot(GraphicsDevice screen) throws AWTException
    {
        super(screen);
        teensyController = new TeensyController();
        motion = new MouseMotion(teensyController);

        // Make sure snippet runs to close connection to Teensy
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            teensyController.close();
            System.out.println("Teensy connection closed");
        }));
    }

    public void delayMS(int ms)
    {
        int min = ms;
        int max = (int) (ms * 2);

        int value = (int) Math.floor(Math.random()*(max-min+1)+min);

        try
        {
            Thread.sleep(value);
        }
        catch (InterruptedException ex)
        {

        }
    }

    public void mouseMoveGeneralLocation(Point p)
    {
        motion.move((int) Math.round(p.x), (int) Math.round(p.y));
    }

    public void leftClick()
    {
        this.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delayMS(85);
        this.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void rightClick()
    {
        this.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        delayMS(85);
        this.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }
}
