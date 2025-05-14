package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;

public class LUA extends Language {
    public LUA() {
        super("LUA");
    }

    @Override
    public void onLoad() {
        System.out.println("Lua plugin loaded");
    }

    @Override
    public @NotNull String getExtension() {
        return "lua";
    }

    @Override
    public @NotNull Environment createEnvironment(final @NotNull Simulator simulator) {
        return new LuaEnvironment(simulator);
    }
}
