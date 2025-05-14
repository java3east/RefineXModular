package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class FiveM extends Context {
    public FiveM() {
        super("FiveM");
    }

    @Override
    public void onLoad() {
        System.out.println("FiveM plugin loaded");
    }

    @Override
    public @NotNull Simulator createDefaultSimulator(@NotNull Simulation simulation) {
        return new Fxmanifest(simulation);
    }
}
