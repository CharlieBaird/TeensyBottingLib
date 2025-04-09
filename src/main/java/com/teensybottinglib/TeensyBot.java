package com.teensybottinglib;

import com.teensybottinglib.InputCodes.KeyCode;
import com.teensybottinglib.InputCodes.MouseCode;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TeensyBot
{
    private final MouseMotionHandler mouseMotionHandler;
    private final TeensyController teensy;

    public TeensyBot()
    {
        teensy = new TeensyController();
        mouseMotionHandler = new MouseMotionHandler(teensy);
        HeldKeys = new HashSet<>();
        HeldMouseClicks = new HashSet<>();

        // Make sure snippet runs to close connection to Teensy
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            teensy.close();
            System.out.println("Teensy connection closed");
        }));
    }

    public void delayMS(int ms)
    {
        int max = ms * 2;
        int value = (int) Math.floor(Math.random() * (max - ms + 1) + ms);

        try {
            Thread.sleep(value);
        } catch (InterruptedException ignored) {
        }
    }

    public void mouseMoveGeneralLocation(Point p)
    {
        mouseMotionHandler.mouseMoveGeneralLocation(p.x, p.y);
    }

    public void mouseMoveExactLocation(Point p)
    {
        mouseMotionHandler.mouseMoveExactLocation(p.x, p.y);
    }

    private final Set<KeyCode> HeldKeys;
    private final Set<MouseCode> HeldMouseClicks;


    public void mouseClick(MouseCode mouseCode)
    {
        String serial = mouseCode.getSerialValue();
        if (!HeldMouseClicks.contains(mouseCode)) {
            HeldMouseClicks.add(mouseCode);
            teensy.mousePress(serial);
            delayMS(50);
            teensy.mouseRelease(serial);
        }
        else {
            mouseRelease(mouseCode);
        }
    }

    public void mousePress(MouseCode mouseCode)
    {
        String serial = mouseCode.getSerialValue();
        if (!HeldMouseClicks.contains(mouseCode)) {
            HeldMouseClicks.add(mouseCode);
            teensy.mousePress(serial);
        }
    }

    public void mouseRelease(MouseCode mouseCode)
    {
        String serial = mouseCode.getSerialValue();
        if (HeldMouseClicks.contains(mouseCode)) {
            HeldMouseClicks.remove(mouseCode);
            teensy.mouseRelease(serial);
        }
    }

    public void keyClick(KeyCode keyCode)
    {
        String serial = keyCode.getSerialValue();
        if (!HeldKeys.contains(keyCode)) {
            HeldKeys.add(keyCode);
            teensy.keyPress(serial);
            delayMS(60);
            teensy.keyRelease(serial);
            HeldKeys.remove(keyCode);
        } else {
            keyRelease(keyCode);
        }
    }

    public void keyPress(KeyCode keyCode)
    {
        String serial = keyCode.getSerialValue();
        if (!HeldKeys.contains(keyCode)) {
            HeldKeys.add(keyCode);
            teensy.keyPress(serial);
        }
    }

    public void keyRelease(KeyCode keyCode)
    {
        String serial = keyCode.getSerialValue();
        if (HeldKeys.contains(keyCode)) {
            HeldKeys.remove(keyCode);
            teensy.keyRelease(serial);
        }
    }

    public void keyCombo(KeyCode... combo)
    {
        for (KeyCode key : combo) {
            keyPress(key);
            delayMS(80);
        }

        delayMS(60);

        for (int i = combo.length - 1; i >= 0; i--) {
            keyRelease(combo[i]);
            delayMS(15);
        }
    }

}
