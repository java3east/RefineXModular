package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

import java.util.HashMap;

public class Fxmanifest implements Simulator {
    private final Simulation simulation;
    private final HashMap<String, Environment> environments = new HashMap<>();

    public Fxmanifest(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public @NotNull Simulation getSimulation() {
        return this.simulation;
    }

    @Override
    public @NotNull Environment createEnvironment(@NotNull String name, @NotNull Language language) {
        Environment env = language.createEnvironment(this);
        environments.put(name, env);
        return env;
    }

    @Override
    public @NotNull Environment getEnvironment(@NotNull String name) {
        return environments.get(name);
    }
}
