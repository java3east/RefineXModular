package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.simulation.Simulation;

/**
 * Represents the FiveM context for the RefineX simulation program.
 */
public class FiveM extends Context {
    /**
     * Creates a new instance of the FiveM context plugin.
     */
    public FiveM() {
        super("FiveM");
        addGenerator("MANIFEST", Fxmanifest::new);
    }

    @Override
    public void onLoad() {
        System.out.println("FiveM plugin loaded");
    }

    @Override
    public @NotNull Manifest createManifest(@NotNull Simulation simulation) {
        return new Fxmanifest(simulation);
    }

    @Override
    public @NotNull String manifestName() {
        return "fxmanifest.lua";
    }
}
