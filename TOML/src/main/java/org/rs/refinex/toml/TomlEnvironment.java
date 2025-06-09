package org.rs.refinex.toml;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;

import java.util.List;
import java.util.Optional;

public class TomlEnvironment implements Environment {
    @Override
    public Optional<Object> get(@NotNull String key) {
        return Optional.empty();
    }

    @Override
    public void set(@NotNull String key, @NotNull Object value, boolean shared) {

    }

    @Override
    public void addNamespace(@NotNull Namespace namespace) {

    }

    @Override
    public void load(@NotNull String str) {

    }

    @Override
    public void loadfile(@NotNull String path) {

    }

    @Override
    public void addEventHandler(@NotNull ContextEventHandler handler) {

    }

    @Override
    public boolean dispatchEvent(@NotNull ContextEvent event) {
        return false;
    }

    @Override
    public void tick(double frameTime) {

    }

    @Override
    public @NotNull LogSource currentSource() {
        return null;
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return null;
    }

    @Override
    public @NotNull List<Namespace> getNamespaces() {
        return List.of();
    }

    @Override
    public @NotNull String getResourcePath() {
        return "";
    }

    @Override
    public @NotNull Resource getResource() {
        return null;
    }
}
