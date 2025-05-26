package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.fivem.simulation.FiveMSimulatorManager;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.plugin.RefineXPlugin;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.SimulatorManager;

import java.util.logging.Level;

/**
 * Represents the FiveM context for the RefineX simulation program.
 */
@RefineXPlugin
public class FiveM extends Context {
    /**
     * Creates a new instance of the FiveM context plugin.
     */
    public FiveM() {
        super("FiveM");
        addGenerator("MANIFEST", Fxmanifest::new);
        addGenerator("SERVER", ServerSimulator::new);
        addGenerator("CLIENT", ClientSimulator::new);
    }

    @Override
    public void onLoad() { }

    @Override
    public @NotNull Simulation createSimulation() {
        Simulation simulation = new Simulation(this);
        simulation.createSimulator("SERVER");
        return simulation;
    }

    @Override
    public @NotNull Manifest createManifest(@NotNull Simulation simulation) {
        return new Fxmanifest(simulation);
    }

    @Override
    public @NotNull String manifestName() {
        return "fxmanifest.lua";
    }

    @Override
    public @NotNull SimulatorManager createSimulatorManager(final @NotNull Simulation simulation) {
        return new FiveMSimulatorManager(simulation);
    }
}
