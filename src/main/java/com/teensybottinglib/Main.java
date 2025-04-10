package com.TeensyBottingLib;

import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        TeensyBot bot = new TeensyBot();

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                bot.mouseMoveGeneralLocation(new Point(500, 500));
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Runnable runnable2 = new Runnable()
        {
            @Override
            public void run()
            {
                bot.mouseMoveGeneralLocation(new Point(1000, 1000));
            }
        };

        Thread thread2 = new Thread(runnable2);
        thread2.start();

        System.out.println("Test");

    }
}