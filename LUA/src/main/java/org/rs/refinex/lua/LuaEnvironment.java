package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A Lua environment for the RefineX simulation program.
 * This class provides an interface to load and execute Lua scripts
 */
public class LuaEnvironment implements Environment {
    private final Simulator simulator;
    private final Globals globals = JsePlatform.debugGlobals();
    private final List<Namespace> namespaces = new ArrayList<>();

    /**
     * Creates a new Lua environment with the given simulator.
     * This will also add some default functions and libraries to the environment.
     * @param simulator the simulator this environment is created for
     */
    public LuaEnvironment(@NotNull Simulator simulator) {
        this.simulator = simulator;
        this.globals.set("PARENT_EXISTS", new ExistsFunction(this));
        this.globals.set("PARENT_CALL", new CallFunction(this));
        this.globals.loadfile("./plugins/lua/lib/lib.lua").call();
        addNamespace(new LuaNamespace());
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

    @Override
    public @NotNull String getResourcePath() {
        return FileUtils.currentDirectory().getAbsolutePath() + "/./resources/";
    }
}
