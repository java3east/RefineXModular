package org.rs.refinex.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;

import java.util.HashMap;

/**
 * Simulators represent a container of simulated scripting environments.
 * They can be used to represent a single client or server.
 *
 * @author Florian B.
 */
public abstract class Simulator {
    private final @NotNull Simulation simulation;
    private final HashMap<String, Environment> environments = new HashMap<>();

    private long gameTimer = 0;

    /**
     * Creates a new simulator for the given simulation.
     * @param simulation the simulation this simulator will be created for
     */
    public Simulator(final @NotNull Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Returns the simulation this simulator belongs to.
     * @return the simulation
     */
    public @NotNull Simulation getSimulation() {
        return this.simulation;
    }

    /**
     * Adds the given namespace to the environment.
     * @param environment the environment to add the namespace to
     */
    protected abstract void addNamespaces(final @NotNull Environment environment);

    /**
     * Creates a new environment that should be bound to this simulator.
     * This will also register the environment in this simulator, so it can be
     * retrieved later with {@link #getEnvironment(String)}
     * @param name the name the environment should be registered with
     * @param language the language the environment should be created for
     * @return the created environment
     */
    public @NotNull Environment createEnvironment(final @NotNull String name, final @NotNull Language language) {
        Environment environment = language.createEnvironment(this);
        addNamespaces(environment);
        environments.put(name, environment);
        return environment;
    }

    /**
     * Returns an environment with the given name.
     * 
     * @throws RuntimeException if the environment does not exist
     * 
     * @param name the name of the environment to retrieve
     * @return the environment with the given name
     */
    public @NotNull Environment getEnvironment(final @NotNull String name) {
        return environments.get(name);
    }

    /**
     * Returns the current game timer of this simulator.
     * @return the current game timer
     */
    public long getGameTimer() {
        return gameTimer;
    }
}
