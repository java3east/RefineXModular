package org.rs.refinex.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;

import java.util.List;

/**
 * Simulators represent a container of simulated scripting environments.
 * They can be used to represent a single client or server.
 *
 * @author Florian B.
 */
public interface Simulator {
    /**
     * Returns the simulation this simulator belongs to.
     * @return the simulation
     */
    @NotNull Simulation getSimulation();

    /**
     * Creates a new environment that should be bound to this simulator.
     * This will also register the environment in this simulator, so it can be
     * retrieved later with {@link #getEnvironment(String)}
     * @param name the name the environment should be registered with
     * @param language the language the environment should be created for
     * @return the created environment
     */
    @NotNull Environment createEnvironment(final @NotNull String name, final @NotNull Language language);

    /**
     * Returns an environment with the given name.
     * 
     * @throws RuntimeException if the environment does not exist
     * 
     * @param name the name of the environment to retrieve
     * @return the environment with the given name
     */
    @NotNull Environment getEnvironment(final @NotNull String name);
}
