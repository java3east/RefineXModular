package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;

public class LuaEnvironment implements Environment {
    private final Simulator simulator;

    public LuaEnvironment(@NotNull Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public void load(@NotNull String str) {

    }

    @Override
    public @NotNull Simulator getSimulator() {
        return this.simulator;
    }
}
