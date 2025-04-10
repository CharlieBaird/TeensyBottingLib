package com.TeensyBottingLib.Utility;

public class Timer
{
    private static long startTime;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static void stop() {
        System.out.println("Execution time: " + getElapsedMillis() + " ms");
    }

    public static long getElapsedNanos() {
        return System.nanoTime() - startTime;
    }

    public static double getElapsedMillis() {
        return getElapsedNanos() / 1_000_000.0;
    }
}

