package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;
import org.rs.refinex.value.Function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    private final HashMap<String, Object> dataStore = new HashMap<>();
    private final HashMap<Long, Function> functionReferences = new HashMap<>();

    private long nextRefId = 1;

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
    public Optional<Object> get(@NotNull String key) {
        if (!dataStore.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(dataStore.get(key));
    }

    @Override
    public Object getGlobal(@NotNull String key) {
        return this.globals.get(key);
    }

    @Override
    public void set(@NotNull String key, @NotNull Object value, boolean shared) {
        dataStore.put(key, value);
        if (shared) {
            globals.set(key, new LuaValueMapper().unmap(value, this));
        }
    }

    @Override
    public void addNamespace(@NotNull Namespace namespace) {
        this.namespaces.add(namespace);
    }

    @Override
    public void load(@NotNull String str) {
        try {
            globals.load(str).call();
        } catch (Exception e) {
            RefineX.logger.log(LogType.ERROR, "An error occurred while executing Lua code: "
                    + e.getMessage(), LogSource.here());
        }
    }

    @Override
    public void loadfile(@NotNull String path) {
        try {
            globals.loadfile(path).call();
        } catch (Exception e) {
            RefineX.logger.log(LogType.ERROR, "An error occurred while loading Lua file: "
                    + e.getMessage(), LogSource.here());
        }
    }

    @Override
    public long functionReference(@NotNull Function function) {
        long id = nextRefId++;
        functionReferences.put(id, function);
        return id;
    }

    @Override
    public Function getFunctionReference(long refId) {
        return functionReferences.get(refId);
    }

    @Override
    public Object envTypeFunctionalObject(Object obj, boolean isStatic) {
        LuaValue value = (LuaValue) obj;
        return this.globals.get("__ref").call(value, LuaValue.valueOf(isStatic));
    }

    @Override
    public void addEventHandler(@NotNull ContextEventHandler handler) {
        List<ContextEventHandler> handlers = eventHandlers.getOrDefault(handler.name(), new ArrayList<>());
        handlers.add(handler);
        eventHandlers.put(handler.name(), handlers);
    }

    @Override
    public boolean dispatchEvent(@NotNull ContextEvent event) {
        List<ContextEventHandler> handlers = eventHandlers.getOrDefault(event.name(), new ArrayList<>());
        if (handlers.isEmpty()) return false;

        for (ContextEventHandler handler : handlers) {
            try {
                handler.handle(event);
            } catch (Exception e) {
                e.printStackTrace();
                RefineX.logger.log(LogType.ERROR, "Error during event handling for " + event.name() + ": "
                        + e.getMessage(), currentSource());
            }
        }
        return true;
    }

    @Override
    public void tick(double frameTime) {
        LuaValue value = this.globals.get("REFINEX_TICK");
        if (value.isfunction()) {
            try {
                value.call(LuaValue.valueOf(frameTime));
            } catch(Exception e) {
                RefineX.logger.log(LogType.ERROR, "An error occurred while executing the REFINEX_TICK function: "
                        + e.getMessage(), currentSource());
            }
        } else
            RefineX.logger.log(LogType.WARNING, "REFINEX_TICK is not a function, skipping tick execution", currentSource());
    }

    @Override
    public @NotNull LogSource currentSource() {
        try {
            LuaTable tbl = globals.get("debug").checktable();
            LuaTable info = tbl.get("getinfo").invoke(LuaValue.valueOf("1"), LuaValue.valueOf("Sl")).checktable(1);
            return new LogSource(info.get("short_src").tojstring(), info.get("currentline").toint());
        } catch(Exception e) {
            return new LogSource("?", -1);
        }
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

    @Override
    public Language getLanguage() {
        return LanguageManager.getLanguage("LUA");
    }
}
