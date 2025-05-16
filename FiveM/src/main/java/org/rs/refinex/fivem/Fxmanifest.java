package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.fivem.namespaces.MANIFEST;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class Fxmanifest extends Simulator {
    public Fxmanifest(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new MANIFEST());
    }
}
