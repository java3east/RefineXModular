package org.rs.refinex.rfx;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class RFX extends Context {
    public RFX() {
        super("RFX");
        addGenerator("RFX", RfxSimulator::new);
    }

    @Override
    public void onLoad() {
        System.out.println("RFX plugin loaded");
    }

    @Override
    public @NotNull Simulator createDefaultSimulator(final @NotNull Simulation simulation) {
        return new RfxSimulator(simulation);
    }

    @Override
    public @NotNull String manifestName() {
        return "runner.lua";
    }
}
