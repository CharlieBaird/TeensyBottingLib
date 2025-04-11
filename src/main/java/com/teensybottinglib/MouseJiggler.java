package com.TeensyBottingLib;

import com.TeensyBottingLib.Utility.SleepUtils;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class MouseJiggler implements Runnable
{
    private volatile boolean running = true;
    private final AsyncTeensyBot bot;

    public MouseJiggler(AsyncTeensyBot bot)
    {
        this.bot = bot;
    }

    @Override
    public void run()
    {
        while (running) {
            int dx;
            int dy;

            int rand = ThreadLocalRandom.current().nextInt(4, 10);
            if (rand >= 8) {
                dx = ThreadLocalRandom.current().nextInt(-8, 9); // -1, 0, or 1
                dy = ThreadLocalRandom.current().nextInt(-8, 9);
            } else {
                dx = ThreadLocalRandom.current().nextInt(-1, 2); // -1, 0, or 1
                dy = ThreadLocalRandom.current().nextInt(-1, 2);
            }

            bot.mouseMoveRelative(new Point(dx, dy));

            // Right offset normal distribution
            SleepUtils.sleep(20, 1200, SleepUtils.BiasType.GAUSSIAN, 0.5, 0.7, false);
        }
    }


    public void stop()
    {
        running = false;
    }
}
