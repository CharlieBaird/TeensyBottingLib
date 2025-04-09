package com.TeensyBottingLib.Utility;

import java.util.concurrent.ThreadLocalRandom;

public class SleepUtils
{

    public enum BiasType {
        UNIFORM,
        FRONT_BIASED,
        BACK_BIASED,
        GAUSSIAN,
        EXPONENTIAL
    }

    // Uses a gaussian distribution (normal-ish)
    public static void delayAround(long minMillis, long maxMillis)
    {
        sleep(minMillis, maxMillis, BiasType.GAUSSIAN, 0.25, 0.0, false);
    }

    // Uses a gaussian distribution (normal-ish)
    public static void delayAround(long millis)
    {
        long minMillis = (long) (millis * 0.7);
        long maxMillis = (long) (millis * 1.3);
        sleep(minMillis, maxMillis, BiasType.GAUSSIAN, 0.25, 0.0, false);
    }

    public static void sleep(long minMillis, long maxMillis, BiasType biasType, double biasStrength) {
        sleep(minMillis, maxMillis, biasType, biasStrength, 0.0, false);
    }

    public static void sleep(long minMillis, long maxMillis, BiasType biasType, double biasStrength,
                             double gaussianSkew, boolean invertExponential) {
        if (minMillis >= maxMillis || biasStrength < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        double r = switch (biasType) {
            case UNIFORM -> ThreadLocalRandom.current().nextDouble();
            case FRONT_BIASED -> Math.pow(ThreadLocalRandom.current().nextDouble(), biasStrength + 1);
            case BACK_BIASED -> 1.0 - Math.pow(1.0 - ThreadLocalRandom.current().nextDouble(), biasStrength + 1);
            case GAUSSIAN -> skewedGaussianNormalized(biasStrength, gaussianSkew);
            case EXPONENTIAL -> {
                double val = exponentialNormalized(biasStrength);
                yield invertExponential ? 1.0 - val : val;
            }
        };

        long sleepTime = minMillis + (long)((maxMillis - minMillis) * clamp(r, 0.0, 1.0));

        System.out.println("Min: " + minMillis + ", Max: " + maxMillis + ", Sleep: " + sleepTime);

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static double skewedGaussianNormalized(double stdFraction, double skew) {
        double mean = 0.5;
        double stdDev = stdFraction * 0.5;

        // Box-Muller
        double u1 = ThreadLocalRandom.current().nextDouble();
        double u2 = ThreadLocalRandom.current().nextDouble();
        double z = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2 * Math.PI * u2);

        double value = mean + z * stdDev;

        // Apply skew (-1 to +1)
        value += skew * stdDev;

        return value;
    }

    private static double exponentialNormalized(double lambda) {
        double u = ThreadLocalRandom.current().nextDouble();
        return 1.0 - Math.exp(-lambda * u); // Bias toward 0
    }

    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static void testDistributions()
    {
//        printHistogram(SleepUtils.BiasType.GAUSSIAN, 0.5, 0.7, false);
//        printHistogram(SleepUtils.BiasType.EXPONENTIAL, 3.0, 0.0, true);
//        printHistogram(SleepUtils.BiasType.GAUSSIAN, 0.25, 0.7, false);
//        printHistogram(BiasType.EXPONENTIAL, 4, 0, true);
    }

    public static void printHistogram(BiasType biasType, double biasStrength,
                                      double gaussianSkew, boolean invertExponential) {
        int[] buckets = new int[20];
        int samples = 30_000;

        for (int i = 0; i < samples; i++) {
            double r = switch (biasType) {
                case UNIFORM -> ThreadLocalRandom.current().nextDouble();
                case FRONT_BIASED -> Math.pow(ThreadLocalRandom.current().nextDouble(), biasStrength + 1);
                case BACK_BIASED -> 1.0 - Math.pow(1.0 - ThreadLocalRandom.current().nextDouble(), biasStrength + 1);
                case GAUSSIAN -> SleepUtils.skewedGaussianNormalized(biasStrength, gaussianSkew);
                case EXPONENTIAL -> {
                    double val = SleepUtils.exponentialNormalized(biasStrength);
                    yield invertExponential ? 1.0 - val : val;
                }
            };

            int bucket = (int) (r * buckets.length);
            if (bucket >= 0 && bucket < buckets.length) {
                buckets[bucket]++;
            }
        }

        // Print histogram
        for (int i = 0; i < buckets.length; i++) {
            String bar = "*".repeat(buckets[i] / 100); // scale down
            System.out.printf("[%02d-%03d%%]: %s%n", i * 5, (i + 1) * 5, bar);
        }

        System.out.println();
    }
}
