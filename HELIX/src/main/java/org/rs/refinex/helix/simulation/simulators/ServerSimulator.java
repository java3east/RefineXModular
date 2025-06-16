package org.rs.refinex.helix.simulation.simulators;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.helix.obj.Package;
import org.rs.refinex.helix.obj.Player;
import org.rs.refinex.helix.obj.ServerEvents;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;

public class ServerSimulator extends Simulator {
    /**
     * Creates a new simulator for the given simulation.
     *
     * @param simulation the simulation this simulator will be created for
     */
    public ServerSimulator(@NotNull Simulation simulation) {
        super(simulation, "SERVER");
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addStaticFunctionInterface("Player", Player.class);
        environment.addStaticFunctionInterface("Package", Package.class);
        environment.addStaticFunctionInterface("Events", ServerEvents.class);
    }

    @Override
    public @NotNull String getName() {
        return "SERVER";
    }

    @Override
    public boolean onResourceStarting(@NotNull Resource resource) {
        return false;
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) {

    }
}
