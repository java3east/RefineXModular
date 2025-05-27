package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class ClientSimulator extends Simulator {
    public ClientSimulator(@NotNull Simulation simulation) {
        super(simulation, "CLIENT");
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {

    }

    @Override
    public @NotNull String getName() {
        return "CLIENT:" + GUID.register(this).guid;
    }
}
