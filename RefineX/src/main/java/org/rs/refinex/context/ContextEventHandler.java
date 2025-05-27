package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.value.Function;

import java.util.HashMap;

public abstract class ContextEventHandler {
    private final @NotNull String name;
    protected final @NotNull Function function;
    private final LogSource logSource;

    public ContextEventHandler(@NotNull String name, @NotNull Function handler, LogSource source) {
        this.name = name;
        this.function = handler;
        this.logSource = source;
    }

    public @NotNull String name() {
        return name;
    }

    public @NotNull LogSource source() {
        return logSource;
    }

    public abstract void handle(@NotNull ContextEvent event);
}
