package org.rs.refinex.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simulator manager is responsible for managing multiple simulators.
 * It is used to create, manage and search for simulators in a simulation.
 */
public abstract class SimulatorManager {
    private final HashMap<String, List<Simulator>> simulators = new HashMap<>();
    private final Simulation simulation;

    public SimulatorManager(final @NotNull Simulation simulation) {
        this.simulation = simulation;
    }

    public Simulator createSimulator(final @NotNull String type) {
        Simulator simulator = this.simulation.getContext().createSimulator(this.simulation, type);
        List<Simulator> simulators = this.simulators.getOrDefault(type, new ArrayList<>());
        simulators.add(simulator);
        this.simulators.put(type, simulators);
        onCreateSimulator(simulator);
        for (Resource resource : this.simulation.getRunningResources()) {
            startResource(simulator, resource);
            simulator.onResourceStart(resource);
        }
        return simulator;
    }

    protected @NotNull Simulator[] getSimulators(final @NotNull String type) {
        return simulators.getOrDefault(type, new ArrayList<>()).toArray(Simulator[]::new);
    }

    protected @NotNull Simulator[] getSimulators() {
        List<Simulator> simulators = this.simulators.values().stream().flatMap(List::stream).toList();
        return simulators.toArray(Simulator[]::new);
    }

    protected abstract void startResource(final @NotNull Simulator simulator, final @NotNull Resource resource);

    protected abstract void onCreateSimulator(final @NotNull Simulator simulator);

    public void startResource(final @NotNull Resource resource) {
        for (Simulator simulator : getSimulators()) {
            boolean cancel = simulator.onResourceStarting(resource);
            if (!cancel) {
                startResource(simulator, resource);
                simulator.onResourceStart(resource);
            }
        }
    }

    public void destroySimulator(final @NotNull Simulator simulator) {
        if (!simulator.destroy())
            return;
        List<Simulator> simulators = this.simulators.get(simulator.getType());
        if (simulators == null || simulators.isEmpty())
            return;

        simulators.remove(simulator);
    }
}
