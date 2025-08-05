package org.rs.refinex.rfx.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.rfx.namespaces.*;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.util.FileUtils;

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
        environment.addNamespace(new RFX());
        environment.addNamespace(new DEPLOY());
        environment.addNamespace(new ENVIRONMENT());
        environment.loadfile(FileUtils.jarDirectory() + "/plugins/rfx/lib/test.lua");
        environment.loadfile(FileUtils.jarDirectory() + "/plugins/rfx/lib/color.lua");
    }

    @Override
    public @NotNull String getName() {
        return "RFXRunner";
    }

    @Override
    public boolean onResourceStarting(@NotNull Resource resource) {
        return false;
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) { }

    @Override
    public boolean destroy() {
        return false;
    }
}
