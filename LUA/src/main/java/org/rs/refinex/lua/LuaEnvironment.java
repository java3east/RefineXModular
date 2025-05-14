package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;

public class LuaEnvironment implements Environment {
    private final Simulator simulator;
    private final Globals globals = JsePlatform.standardGlobals();

    public LuaEnvironment(@NotNull Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public void load(@NotNull String str) {
        globals.load(str).call();
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return this.simulator;
    }
}
