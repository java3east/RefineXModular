package org.rs.refinex.rfx.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.rfx.namespaces.RESOURCE;
import org.rs.refinex.rfx.namespaces.SIMULATION;
import org.rs.refinex.rfx.namespaces.SIMULATOR;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;

/**
 * RFX simulator manifest.
 * Files of this type is the starting point for simulations.
 */
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
        environment.addNamespace(new SIMULATOR());
        environment.addNamespace(new RESOURCE());
    }
}
