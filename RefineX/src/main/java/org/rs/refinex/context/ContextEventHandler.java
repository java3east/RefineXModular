package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Function;

public abstract class ContextEventHandler {
    private final @NotNull String name;
    protected final @NotNull Function function;
    protected final @NotNull Environment environment;
    private final LogSource logSource;

    public ContextEventHandler(@NotNull Environment environment, @NotNull String name, @NotNull Function handler, LogSource source) {
        this.name = name;
        this.function = handler;
        this.logSource = source;
        this.environment = environment;
    }

    public @NotNull String name() {
        return name;
    }

    public @NotNull LogSource source() {
        return logSource;
    }

    public abstract void handle(@NotNull ContextEvent event);
}
