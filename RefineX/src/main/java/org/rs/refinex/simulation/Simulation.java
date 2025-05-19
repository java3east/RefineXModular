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

    public Simulation(final @NotNull Context context) {
        this.context = context;
    }

    public Simulator createSimulator(final @NotNull String type) {
        return context.createSimulator(this, type);
    }

    public @NotNull Manifest defaultSimulator() {
        return context.createManifest(this);
    }

    public @NotNull Context getContext() {
        return context;
    }

    public @NotNull Resource load(String path) {
        return resourceManager.load(path);
    }
}
