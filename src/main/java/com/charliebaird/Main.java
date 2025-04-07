package com.charliebaird;

import java.awt.*;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        try {
            SmartBot bot = SmartBot.getDefault(1);

            bot.mouseMoveGeneralLocation(new Point(500, 500));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Test");
        }

//        teensy.mouseMove(50, 60);
//        Thread.sleep(100);
//
//        teensy.mouseClick("left");
//        Thread.sleep(100);
//
//        teensy.keyPress("a", "SHIFT");
//        Thread.sleep(50);
//        teensy.keyRelease("a");
    }
}