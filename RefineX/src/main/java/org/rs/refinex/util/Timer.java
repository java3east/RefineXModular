package org.rs.refinex.util;

public class Timer {
    public record Time(long nanos, long millis, long seconds, long minutes, long hours) {}

    private long startTime = System.nanoTime();

    public void reset() {
        startTime = System.nanoTime();
    }

    public long nanos() {
        return System.nanoTime() - startTime;
    }

    public long millis() {
        return (System.nanoTime() - startTime) / 1_000_000;
    }

    public long seconds() {
        return (System.nanoTime() - startTime) / 1_000_000_000;
    }

    public long minutes() {
        return seconds() / 60;
    }

    public long hours() {
        return minutes() / 60;
    }

    public Time time() {
        long nanos = nanos();
        long hours = nanos / 3_600_000_000_000L;
        nanos %= 3_600_000_000_000L;
        long minutes = nanos / 60_000_000_000L;
        nanos %= 60_000_000_000L;
        long seconds = nanos / 1_000_000_000;
        nanos %= 1_000_000_000;
        long millis = nanos / 1_000_000;
        nanos %= 1_000_000;
        return new Time(nanos, millis, seconds, minutes, hours);
    }
}
