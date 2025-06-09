package org.rs.refinex.helix.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class SimulatorManager extends org.rs.refinex.simulation.SimulatorManager {
    public SimulatorManager(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    protected void startResource(@NotNull Simulator simulator, @NotNull Resource resource) {

    }

    @Override
    protected void onCreateSimulator(@NotNull Simulator simulator) {

    }
}
