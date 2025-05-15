package org.rs.refinex.rfx;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.rfx.namespaces.Test;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

import java.util.HashMap;

public class RfxSimulator implements Simulator {
    private final Simulation simulation;
    private final HashMap<String, Environment> environments = new HashMap<>();

    public RfxSimulator(final @NotNull Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public @NotNull Simulation getSimulation() {
        return simulation;
    }

    @Override
    public @NotNull Environment createEnvironment(@NotNull String name, @NotNull Language language) {
        Environment environment = language.createEnvironment(this);
        environment.addNamespace(new Test());
        environments.put(name, environment);
        return environment;
    }

    @Override
    public @NotNull Environment getEnvironment(@NotNull String name) {
        return environments.get(name);
    }
}
