package org.rs.refinex.plugin;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextManager;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.SimulatorGenerator;

import java.util.HashMap;
import java.util.function.Function;

/**
 * A context plugin can be used to add support for a new context (game) to RefineX.
 * It tells RefineX how to interact with the context and how to run scripts in that context.
 * This class should be extended by any plugin that wants to add support for a new context.
 *
 * @author Florian B.
 */
public abstract class Context implements Plugin {
    private final String contextName;
    private final HashMap<String, SimulatorGenerator> generators = new HashMap<>();

    public Context(final String contextName) {
        this.contextName = contextName;
        ContextManager.register(this);
    }

    protected void addGenerator(final @NotNull String name, final @NotNull SimulatorGenerator generator) {
        if (generators.containsKey(name))
            throw new RuntimeException("Generator already registered: " + name);

        generators.put(name, generator);
    }

    public abstract @NotNull Manifest createManifest(final @NotNull Simulation simulation);

    public abstract @NotNull String manifestName();

    public @NotNull Simulator createSimulator(final @NotNull Simulation simulation, final @NotNull String type) {
        SimulatorGenerator generator = generators.get(type);
        if (generator == null)
            throw new RuntimeException("Unknown Simulator type: " + type);

        return (Simulator) generator.generate(simulation);
    }

    @Override
    public @NotNull String getName() {
        return contextName;
    }

    @Override
    public String toString() {
        return "PLUGIN{context=" + contextName + "}";
    }
}
