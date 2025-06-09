package org.rs.refinex.toml;

import com.moandjiezana.toml.Toml;
import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TomlEnvironment implements Environment {
    private final Map<String, Object> toml = new HashMap<>();
    private final HashMap<String, Object> dataStore = new HashMap<>();
    private final Simulator simulator;
    private final Resource resource;

    public TomlEnvironment(Simulator simulator, Resource resource) {
        this.simulator = simulator;
        this.resource = resource;
    }

    @Override
    public Optional<Object> get(@NotNull String key) {
        if (!dataStore.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(dataStore.get(key));
    }

    @Override
    public void set(@NotNull String key, @NotNull Object value, boolean shared) {
        dataStore.put(key, value);
        if (shared) {
            toml.put(key, value);
        }
    }

    @Override
    public void addNamespace(@NotNull Namespace namespace) { }

    @Override
    public void load(@NotNull String str) {
        Map<String, Object> toml = new Toml().read(str).toMap();
        for (Map.Entry<String, Object> entry : toml.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            this.toml.put(key, value);
        }
    }

    @Override
    public void loadfile(@NotNull String path) {
        Map<String, Object> toml = new Toml().read(new File(path)).toMap();
        for (Map.Entry<String, Object> entry : toml.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            this.toml.put(key, value);
        }
    }

    @Override
    public void addEventHandler(@NotNull ContextEventHandler handler) { }

    @Override
    public boolean dispatchEvent(@NotNull ContextEvent event) {
        return false;
    }

    @Override
    public void tick(double frameTime) { }

    @Override
    public @NotNull LogSource currentSource() {
        return LogSource.here();
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return this.simulator;
    }

    @Override
    public @NotNull List<Namespace> getNamespaces() {
        return List.of();
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
