package org.rs.refinex.rfx;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.plugin.RefineXPlugin;
import org.rs.refinex.simulation.Simulation;

/**
 * RFX plugin for Refinex.
 * This plugin provides a simulation context for the RFX simulator.
 */
@RefineXPlugin
public class RFX extends Context {
    /**
     * Creates a new RFX plugin instance.
     */
    public RFX() {
        super("RFX");
        addGenerator("RFX", RfxSimulator::new);
    }

    @Override
    public void onLoad() {
        System.out.println("RFX plugin loaded");
    }

    @Override
    public @NotNull Manifest createManifest(final @NotNull Simulation simulation) {
        return new RfxSimulator(simulation);
    }

    @Override
    public @NotNull String manifestName() {
        return "runner.lua";
    }
}
