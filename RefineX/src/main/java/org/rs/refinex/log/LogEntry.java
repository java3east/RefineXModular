package org.rs.refinex.log;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public record LogEntry(Date date, LogType type, String message, LogSource source) {
    @Override
    public @NotNull String toString() {
        String dateStr = String.format("%tT", date);
        return String.format("[%s] %s%s " + LogColor.WHITE + "(%s)" + LogColor.RESET, dateStr, type.getTag(), message, source.toString());
    }
}
