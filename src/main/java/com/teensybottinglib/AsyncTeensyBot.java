package com.TeensyBottingLib;

import com.TeensyBottingLib.InputCodes.KeyCode;
import com.TeensyBottingLib.InputCodes.MouseCode;
import com.TeensyBottingLib.Utility.Timer;

import java.awt.Point;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncTeensyBot
{

    private final TeensyBot bot;
    private final ExecutorService executor;

    public AsyncTeensyBot()
    {
        this.bot = new TeensyBot();
        this.executor = Executors.newCachedThreadPool();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shutdown();
            System.out.println("AsyncTeensyBot executor shut down");
        }));
    }

    private void run(Runnable task, boolean async)
    {
        if (async) {
            executor.submit(task);
        } else {
            task.run();
        }
    }

    // --- Mouse Operations ---

    public void mouseMoveGeneralLocation(Point p, boolean async)
    {
        run(() -> bot.mouseMoveGeneralLocation(p), async);
    }

    public void mouseMoveGeneralLocation(Point p, int proximity, boolean async)
    {
        run(() -> bot.mouseMoveGeneralLocation(p, proximity), async);
    }

    public void mouseMoveExactLocation(Point p, boolean async)
    {
        run(() -> bot.mouseMoveExactLocation(p), async);
    }

    public void mouseMoveRelative(Point p)
    {
        run(() -> bot.mouseMoveRelative(p), false);
    }

    public void mouseClick(MouseCode code, boolean async)
    {
        run(() -> bot.mouseClick(code), async);
    }

    public void mouseClickOnceOrTwice(MouseCode code, boolean async)
    {
        run(() -> bot.mouseClickOnceOrTwice(code), async);
    }

    public void mouseClickForDuration(MouseCode code, int minDur, int maxDur, boolean async)
    {
        run(() -> bot.mouseClickForDuration(code, minDur, maxDur), async);
    }

    public void mousePress(MouseCode code, boolean async)
    {
        run(() -> bot.mousePress(code), async);
    }

    public void mouseRelease(MouseCode code, boolean async)
    {
        run(() -> bot.mouseRelease(code), async);
    }

    // --- Key Operations ---

    public void keyClick(KeyCode key, boolean async)
    {
        run(() -> bot.keyClick(key), async);
    }

    public void keyPress(KeyCode key, boolean async)
    {
        run(() -> bot.keyPress(key), async);
    }

    public void keyRelease(KeyCode key, boolean async)
    {
        run(() -> bot.keyRelease(key), async);
    }

    public void keyCombo(boolean async, KeyCode... combo)
    {
        run(() -> bot.keyCombo(combo), async);
    }

    // --- Shutdown ---

    public void shutdown()
    {
        try {
            executor.shutdown();
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
