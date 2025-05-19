package org.rs.refinex.rfx;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.rfx.namespaces.SIMULATION;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;


public class RfxSimulator extends Manifest {
    public RfxSimulator(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    public void set(@NotNull String key, @NotNull String value) { }

    @Override
    public String[] get(@NotNull String key) {
        return new String[0];
    }

    @Override
    public void validate() { }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new SIMULATION());
    }
}
