package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.fivem.namespaces.CFX;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

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
        environment.addNamespace(new CFX());
    }

    @Override
    public @NotNull String getName() {
        return "SERVER";
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) {
        new ContextEvent(LogSource.here(), "onResourceStart", null, this, resource.getName()).dispatch();
    }
}
