package org.rs.refinex.plugin;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextManager;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

/**
 * A context plugin can be used to add support for a new context (game) to RefineX.
 * It tells RefineX how to interact with the context and how to run scripts in that context.
 * This class should be extended by any plugin that wants to add support for a new context.
 *
 * @author Florian B.
 */
public abstract class Context implements Plugin {
    private final String contextName;

    public Context(final String contextName) {
        this.contextName = contextName;
        ContextManager.register(this);
    }

    public abstract @NotNull Simulator createDefaultSimulator(final @NotNull Simulation simulation);

    @Override
    public @NotNull String getName() {
        return contextName;
    }

    @Override
    public String toString() {
        return "PLUGIN{context=" + contextName + "}";
    }
}
