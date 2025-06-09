package org.rs.refinex.log;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private final List<LogEntry> logs = new ArrayList<>();
    private boolean instantLogging = true;

    public void setInstantLoggingEnabled(boolean enabled) {
        this.instantLogging = enabled;
    }

    public void log(LogEntry entry) {
        logs.add(entry);
        if (instantLogging)
            System.out.println(entry);
    }

    public void log(LogType type, String message, LogSource source) {
        LogEntry entry = new LogEntry(new java.util.Date(), type, message, source);
        log(entry);
    }

    public void catchErrors(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log(LogType.ERROR, "An error occurred: " + e.getMessage() + "[type = " + e.getClass() + "]", LogSource.here());
        }
    }

    public List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }
}
