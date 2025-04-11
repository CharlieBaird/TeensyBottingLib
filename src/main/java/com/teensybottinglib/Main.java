package com.TeensyBottingLib;

import com.TeensyBottingLib.Utility.Timer;

import java.awt.*;

public class Main
{
    private static MouseJiggler mouseJiggler;
    private static Thread mouseJigglerThread;

    public static void main(String[] args) throws InterruptedException
    {
        AsyncTeensyBot bot = new AsyncTeensyBot();

        mouseJiggler = new MouseJiggler(bot);
        mouseJigglerThread = new Thread(mouseJiggler);
        mouseJigglerThread.start();

        Timer.start();
        bot.mouseMoveGeneralLocation(new Point(500, 500), true);
        Timer.stop();

        Thread.sleep(450);

        bot.mouseMoveGeneralLocation(new Point(1000, 1000), true);

        bot.mouseMoveRelative(new Point(0, -300));

        Timer.stop();

//        Thread.sleep(1000);
//        System.exit(0);
    }
}