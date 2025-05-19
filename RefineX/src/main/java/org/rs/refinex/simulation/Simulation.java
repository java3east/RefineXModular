package org.rs.refinex.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.scripting.ResourceManager;

/**
 * A simulation can contain multiple Simulators. It represents a full server-client scripting
 * environment.
 *
 * @author Florian B.
 */
public class Simulation {
    private final Context context;
    private final ResourceManager resourceManager = new ResourceManager(this);

    /**
     * Creates a new simulation with the given context.
     * @param context the context this simulation will be created for
     */
    public Simulation(final @NotNull Context context) {
        this.context = context;
    }

    /**
     * Creates a new simulator for this simulation.
     * This will create a new simulator with the given type.
     * @return the created simulator
     */
    public Simulator createSimulator(final @NotNull String type) {
        return context.createSimulator(this, type);
    }

    /**
     * Creates the default simulator for this simulation.
     * This is usually the manifest simulator.
     * @return the default simulator
     */
    public @NotNull Manifest defaultSimulator() {
        return context.createManifest(this);
    }

    /**
     * Returns the context plugin this simulation is created for.
     * @return the context plugin
     */
    public @NotNull Context getContext() {
        return context;
    }

    /**
     * Loads the given resource.
     * @param path the path to the resource folder
     * @return the loaded resource
     */
    public @NotNull Resource load(String path) {
        return resourceManager.load(path);
    }
}
