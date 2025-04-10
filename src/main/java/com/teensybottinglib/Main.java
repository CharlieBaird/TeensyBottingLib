package com.TeensyBottingLib;

import com.TeensyBottingLib.Utility.Timer;

import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        AsyncTeensyBot bot = new AsyncTeensyBot();

        Timer.start();
        bot.mouseMoveGeneralLocation(new Point(500, 500), true);
        Timer.stop();

        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bot.mouseMoveGeneralLocation(new Point(1000, 1000), true);

        Timer.stop();

    }
}