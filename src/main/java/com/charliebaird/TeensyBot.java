package com.charliebaird;

import com.charliebaird.InputCodes.KeyCode;
import com.charliebaird.InputCodes.MouseCode;
import com.charliebaird.Utility.SleepUtils;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TeensyBot
{
    private final MouseMotionHandler mouseMotionHandler;
    private final TeensyIO teensy;

    public TeensyBot()
    {
        teensy = new TeensyIO();
        mouseMotionHandler = new MouseMotionHandler(teensy);
        HeldKeys = new HashSet<>();
        HeldMouseClicks = new HashSet<>();

        // Make sure snippet runs to close connection to Teensy
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            teensy.close();
            System.out.println("Teensy connection closed");

            // Safety: unpress / unclick anything held down on a crash
            for (KeyCode key : HeldKeys)
            {
                System.out.println("Exiting, releasing "+ key.getSerialValue());
                SleepUtils.delayAround(40);
                keyRelease(key);
                SleepUtils.delayAround(40);
            }

            for (MouseCode key : HeldMouseClicks)
            {
                System.out.println("Exiting, releasing "+ key.getSerialValue());
                SleepUtils.delayAround(40);
                mouseRelease(key);
                SleepUtils.delayAround(40);
            }
        }));
    }

    public void mouseMoveGeneralLocation(Point p)
    {
        mouseMotionHandler.mouseMoveGeneralLocation(p.x, p.y);
    }

    public void mouseMoveExactLocation(Point p)
    {
        mouseMotionHandler.mouseMoveExactLocation(p.x, p.y);
    }

    public void mouseMoveRelative(Point p)
    {
        mouseMotionHandler.mouseMoveRelative(p.x, p.y);
    }

    private final Set<KeyCode> HeldKeys;
    private final Set<MouseCode> HeldMouseClicks;

    public void mouseClickForDuration(MouseCode mouseCode, int minDur, int maxDur)
    {
        if (minDur < 50) minDur = 50;

        String serial = mouseCode.getSerialValue();
        if (!HeldMouseClicks.contains(mouseCode)) {
            HeldMouseClicks.add(mouseCode);
            teensy.mousePress(serial);
            SleepUtils.sleep(minDur, maxDur, SleepUtils.BiasType.EXPONENTIAL, 3, 0, true);

            teensy.mouseRelease(serial);
            HeldMouseClicks.remove(mouseCode);
        }
        else {
            mouseRelease(mouseCode);
        }
    }

    public void mouseClick(MouseCode mouseCode)
    {
        String serial = mouseCode.getSerialValue();
        if (!HeldMouseClicks.contains(mouseCode)) {
            HeldMouseClicks.add(mouseCode);
            teensy.mousePress(serial);
            SleepUtils.delayAround(75);
            teensy.mouseRelease(serial);
            HeldMouseClicks.remove(mouseCode);
        }
        else {
            mouseRelease(mouseCode);
        }
    }

    public void mousePress(MouseCode mouseCode)
    {
        String serial = mouseCode.getSerialValue();
        if (!HeldMouseClicks.contains(mouseCode)) {
            System.out.println("Pressing " + serial);
            HeldMouseClicks.add(mouseCode);
            teensy.mousePress(serial);
        }
        else
        {
            System.out.println("Already pressing " + serial);
        }
    }

    public void mouseRelease(MouseCode mouseCode)
    {
        String serial = mouseCode.getSerialValue();
        if (HeldMouseClicks.contains(mouseCode)) {
            System.out.println("Releasing " + serial);
            HeldMouseClicks.remove(mouseCode);
            teensy.mouseRelease(serial);
        }
        else {
            System.out.println("Cannot release " + serial);
        }
    }

    public void keyClick(KeyCode keyCode)
    {
        String serial = keyCode.getSerialValue();
        if (!HeldKeys.contains(keyCode)) {
            HeldKeys.add(keyCode);
            teensy.keyPress(serial);
            SleepUtils.delayAround(85);
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
            SleepUtils.delayAround(110);
        }

        SleepUtils.delayAround(85);

        for (int i = combo.length - 1; i >= 0; i--) {
            keyRelease(combo[i]);
            SleepUtils.delayAround(25);
        }
    }

}
