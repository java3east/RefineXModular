package org.rs.refinex.scripting;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.simulation.Simulator;

/**
 * Environment represents a scripting environment, they are responsible for
 * loading and executing scripts.
 * Each environment is bound to a simulator.
 */
public interface Environment {
    void load(final @NotNull String str);
    @NotNull Simulator getSimulator();
}
