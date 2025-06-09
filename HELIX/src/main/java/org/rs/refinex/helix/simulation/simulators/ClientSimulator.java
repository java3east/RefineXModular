package org.rs.refinex.helix.simulation.simulators;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.helix.namespace.PACKAGE;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;

public class ClientSimulator extends Simulator {
    public ClientSimulator(@NotNull Simulation simulation) {
        super(simulation, "CLIENT");
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new PACKAGE());
        environment.loadfile(FileUtils.jarDirectory() + "/plugins/helix/lib/shared/package.lua");
    }

    @Override
    public @NotNull String getName() {
        return "CLIENT:" + GUID.register(this).guid;
    }

    @Override
    public boolean onResourceStarting(@NotNull Resource resource) {
        return false;
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) {

    }
}
