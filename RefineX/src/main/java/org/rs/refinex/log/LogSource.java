package org.rs.refinex.log;

import org.jetbrains.annotations.NotNull;

public record LogSource(String file, int line) {
    public static @NotNull LogSource here() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 3) {
            return new LogSource("Unknown", -1);
        }
        StackTraceElement element = stackTrace[2];
        String fileName = element.getClassName();
        int lineNumber = element.getLineNumber();
        return new LogSource(fileName, lineNumber);
    }
    @Override
    public @NotNull String toString() {
        return String.format("@%s:%d", file, line);
    }
}
