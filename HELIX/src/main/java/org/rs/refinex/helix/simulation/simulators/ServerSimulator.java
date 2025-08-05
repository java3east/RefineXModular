package org.rs.refinex.helix.simulation.simulators;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.helix.namespaces.SERVER;
import org.rs.refinex.helix.obj.*;
import org.rs.refinex.helix.obj.Package;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Varargs;

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
        environment.addStaticFunctionInterface("Package", Package.class);
        environment.addStaticFunctionInterface("Events", ServerEvents.class);
        environment.addStaticFunctionInterface("Server", Server.class);
        environment.addStaticFunctionInterface("HPlayer", HPlayer.class);
        environment.addStaticFunctionInterface("Vector", Vector.class);
        environment.addNamespace(new SERVER());
        environment.setMeta("Vector", Vector.class);
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
        Environment environment = this.getEnvironment("LUA_" + resource.getName());
        environment.dispatchEvent(
            new ContextEvent(LogSource.here(), "Start", null, this, Varargs.of())
        );
    }

    @Override
    public boolean destroy() {
        return false;
    }
}
