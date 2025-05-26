package org.rs.refinex.log;

public enum LogType {
    DEBUG(0, LogColor.RESET, LogColor.RESET, false),
    INFO(1, LogColor.BLUE, LogColor.RESET, false),
    WARNING(2, LogColor.YELLOW, LogColor.YELLOW, false),
    ERROR(3, LogColor.RED, LogColor.RED, true)
    ;
    private final int level;
    private final LogColor color;
    private final LogColor msgColor;
    private final boolean bold;

    LogType(int level, LogColor color, LogColor msgColor, boolean bold) {
        this.level = level;
        this.color = color;
        this.msgColor = msgColor;
        this.bold = bold;
    }

    public String getTag() {
        return (bold ? LogColor.BOLD.ansi() : "") + LogColor.WHITE + "[" + this.color + this.name() + LogColor.WHITE + "]" + msgColor + " ";
    }

    public boolean isHigherThan(LogType other) {
        return this.level > other.level;
    }
}
