package org.rs.refinex.helix.simulation.simulators;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class ClientSimulator extends Simulator {
    /**
     * Creates a new simulator for the given simulation.
     *
     * @param simulation the simulation this simulator will be created for
     * @param type
     */
    public ClientSimulator(@NotNull Simulation simulation) {
        super(simulation, "CLIENT");
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {

    }

    @Override
    public @NotNull String getName() {
        return "";
    }

    @Override
    public boolean onResourceStarting(@NotNull Resource resource) {
        return false;
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) {

    }
}
