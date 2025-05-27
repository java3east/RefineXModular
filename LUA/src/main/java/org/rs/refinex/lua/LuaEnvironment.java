package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;
import org.rs.refinex.value.Function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Lua environment for the RefineX simulation program.
 * This class provides an interface to load and execute Lua scripts
 */
public class LuaEnvironment implements Environment {
    private final Simulator simulator;
    private final Resource resource;
    private final Globals globals = JsePlatform.debugGlobals();
    private final List<Namespace> namespaces = new ArrayList<>();
    private final HashMap<String, List<ContextEventHandler>> eventHandlers = new HashMap<>();

    /**
     * Creates a new Lua environment with the given simulator.
     * This will also add some default functions and libraries to the environment.
     * @param simulator the simulator this environment is created for
     */
    public LuaEnvironment(@NotNull Simulator simulator, @NotNull Resource resource) {
        this.simulator = simulator;
        this.resource = resource;
        this.globals.set("PARENT_EXISTS", new ExistsFunction(this));
        this.globals.set("PARENT_CALL", new CallFunction(this));
        this.globals.loadfile(FileUtils.jarDirectory() + "/plugins/lua/lib/lib.lua").call();
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
    public void addEventHandler(@NotNull ContextEventHandler handler) {
        List<ContextEventHandler> handlers = eventHandlers.getOrDefault(handler.name(), new ArrayList<>());
        handlers.add(handler);
        eventHandlers.put(handler.name(), handlers);
    }

    @Override
    public void dispatchEvent(@NotNull ContextEvent event) {
        List<ContextEventHandler> handlers = eventHandlers.getOrDefault(event.name(), new ArrayList<>());
        for (ContextEventHandler handler : handlers) {
            handler.handle(event);
        }
    }

    @Override
    public @NotNull LogSource currentSource() {
        LuaTable tbl = globals.get("debug").checktable();
        LuaTable info = tbl.get("getinfo").invoke(LuaValue.valueOf("1"), LuaValue.valueOf("Sl")).checktable(1);
        return new LogSource(info.get("short_src").tojstring(), info.get("currentline").toint());
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

    @Override
    public @NotNull Resource getResource() {
        return this.resource;
    }
}
