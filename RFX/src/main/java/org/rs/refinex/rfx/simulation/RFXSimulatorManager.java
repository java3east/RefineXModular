package org.rs.refinex.rfx.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.simulation.SimulatorManager;

public class RFXSimulatorManager extends SimulatorManager {
    public RFXSimulatorManager(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    protected void startResource(@NotNull Simulator simulator, @NotNull Resource resource) {}

    @Override
    protected void onCreateSimulator(@NotNull Simulator simulator) { }

    @Override
    public void startResource(@NotNull Resource resource) {}
}
