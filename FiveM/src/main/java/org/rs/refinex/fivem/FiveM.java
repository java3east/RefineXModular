package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.simulation.Simulation;

public class FiveM extends Context {
    public FiveM() {
        super("FiveM");
        addGenerator("MANIFEST", Fxmanifest::new);
        addGenerator("SERVER", Server::new);
        addGenerator("CLIENT", Client::new);
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
