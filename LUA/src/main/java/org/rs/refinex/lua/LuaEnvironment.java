package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;

import java.util.ArrayList;
import java.util.List;

public class LuaEnvironment implements Environment {
    private final Simulator simulator;
    private final Globals globals = JsePlatform.standardGlobals();
    private final List<Namespace> namespaces = new ArrayList<>();

    public LuaEnvironment(@NotNull Simulator simulator) {
        this.simulator = simulator;
        this.globals.set("PARENT_EXISTS", new ExistsFunction(this));
        this.globals.set("PARENT_CALL", new CallFunction(this));
        this.globals.loadfile("./plugins/lua/lib/lib.lua").call();
    }

    @Override
    public void addNamespace(@NotNull Namespace namespace) {
        this.namespaces.add(namespace);
    }

    @Override
    public void load(@NotNull String str) {
        globals.load(str).call();
    }

    @Override
    public void loadfile(@NotNull String path) {
        globals.loadfile(path).call();
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return this.simulator;
    }

    @Override
    public @NotNull List<Namespace> getNamespaces() {
        return namespaces.stream().toList();
    }
}
