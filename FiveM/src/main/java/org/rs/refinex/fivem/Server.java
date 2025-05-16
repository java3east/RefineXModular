package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class Server extends Simulator {
    public Server(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {

    }
}
