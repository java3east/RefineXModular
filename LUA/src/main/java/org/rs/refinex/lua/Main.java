package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;

public class Main extends Language {
    public Main() {
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
    public @NotNull Environment createEnvironment() {
        return null;
    }
}
