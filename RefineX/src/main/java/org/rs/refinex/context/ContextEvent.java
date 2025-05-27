package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;

public record ContextEvent (@NotNull LogSource origin, @NotNull String name, Environment source, @NotNull Simulator target, @NotNull Object... data) {
    public void dispatch() {
        target.dispatchEvent(this);
    }
}
