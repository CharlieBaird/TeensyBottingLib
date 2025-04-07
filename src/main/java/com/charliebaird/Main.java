package com.charliebaird;

import com.charliebaird.InputCodes.KeyCode;
import com.charliebaird.InputCodes.MouseCode;

import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        TeensyBot teensyBot = new TeensyBot();

        teensyBot.mouseMoveGeneralLocation(new Point(500, 500));

        teensyBot.mouseClick(MouseCode.LEFT);

        teensyBot.keyCombo(KeyCode.LCTRL, KeyCode.A);

//        teensyBot.keyClick(KeyCode.A);
//
//        teensyBot.keyPress(KeyCode.LSHIFT);
//        teensyBot.keyPress(KeyCode.A);
//        teensyBot.delayMS(1000);
//        teensyBot.keyRelease(KeyCode.A);
//        teensyBot.keyRelease(KeyCode.LSHIFT);

//        bot.mouseMoveExactLocation(new Point(500, 500));

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