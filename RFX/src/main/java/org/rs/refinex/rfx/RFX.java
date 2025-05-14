package org.rs.refinex.rfx;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class RFX extends Context {
    public RFX() {
        super("RFX");
    }

    @Override
    public void onLoad() {
        System.out.println("RFX plugin loaded");
    }

    @Override
    public @NotNull Simulator createDefaultSimulator(final @NotNull Simulation simulation) {
        return new RfxSimulator(simulation);
    }
}
