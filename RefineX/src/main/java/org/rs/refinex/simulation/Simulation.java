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
    private final ResourceManager resourceManager;
    private final SimulatorManager simulatorManager;

    /**
     * Creates a new simulation with the given context.
     * @param context the context this simulation will be created for
     */
    public Simulation(final @NotNull Context context) {
        this.context = context;
        this.resourceManager = new ResourceManager(this);
        this.simulatorManager = context.createSimulatorManager(this);
    }

    /**
     * Starts the given resource on all simulators managed by this simulation.
     * @param resource the resource to start
     */
    public void startResource(final @NotNull Resource resource) {
        simulatorManager.startResource(resource);
    }

    public @NotNull Resource[] getRunningResources() {
        return this.resourceManager.getRunning();
    }

    /**
     * Creates a new simulator for this simulation.
     * This will create a new simulator with the given type.
     * @return the created simulator
     */
    public Simulator createSimulator(final @NotNull String type) {
        return simulatorManager.createSimulator(type);
    }

    public Simulator[] getSimulator(final @NotNull String type) {
        return simulatorManager.getSimulators(type);
    }

    /**
     * Creates the default simulator for this simulation.
     * This is usually the manifest simulator.
     * @return the default simulator
     */
    public @NotNull Manifest manifest() {
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
