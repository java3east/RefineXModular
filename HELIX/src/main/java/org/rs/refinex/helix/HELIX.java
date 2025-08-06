package org.rs.refinex.helix;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.helix.simulation.HelixManifest;
import org.rs.refinex.helix.simulation.simulators.ClientSimulator;
import org.rs.refinex.helix.simulation.simulators.ServerSimulator;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.plugin.RefineXPlugin;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.SimulatorManager;

@RefineXPlugin
public class HELIX extends Context {
    public HELIX() {
        super("HELIX");
        addGenerator("MANIFEST", HelixManifest::new);
        addGenerator("SERVER", ServerSimulator::new);
        addGenerator("CLIENT", ClientSimulator::new);
    }

    @Override
    public @NotNull Simulation createSimulation() {
        Simulation simulation = new Simulation(this);
        simulation.createSimulator("SERVER");
        return simulation;
    }

    @Override
    public @NotNull Manifest createManifest(@NotNull Simulation simulation) {
        return new HelixManifest(simulation);
    }

    @Override
    public @NotNull String manifestName() {
        return "package.json";
    }

    @Override
    public @NotNull SimulatorManager createSimulatorManager(@NotNull Simulation simulation) {
        return new org.rs.refinex.helix.simulation.SimulatorManager(simulation);
    }

    @Override
    public void onLoad() {

    }
}
