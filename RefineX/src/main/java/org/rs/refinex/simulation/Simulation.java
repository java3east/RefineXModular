package org.rs.refinex.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Context;

/**
 * A simulation can contain multiple Simulators. It represents a full server-client scripting
 * environment.
 *
 * @author Florian B.
 */
public class Simulation {
    private final Context context;

    public Simulation(final @NotNull Context context) {
        this.context = context;
    }

    public Simulator createSimulator(final @NotNull String type) {
        return context.createSimulator(this, type);
    }

    public @NotNull Simulator defaultSimulator() {
        return context.createDefaultSimulator(this);
    }

    public @NotNull Context getContext() {
        return context;
    }
}
