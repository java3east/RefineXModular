package org.rs.refinex.helix.simulation.simulators;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.helix.obj.ClientEvents;
import org.rs.refinex.helix.obj.HPlayer;
import org.rs.refinex.helix.obj.Package;
import org.rs.refinex.helix.obj.Vector;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Varargs;

public class ClientSimulator extends Simulator {
    private final HPlayer player = new HPlayer(this);

    public ClientSimulator(@NotNull Simulation simulation) {
        super(simulation, "CLIENT");
    }

    /**
     * Returns the player associated with this client simulator.
     *
     * @return The Player object representing the client.
     */
    public HPlayer getPlayer() {
        return player;
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addStaticFunctionInterface("Events", ClientEvents.class);
        environment.addStaticFunctionInterface("Package", Package.class);
        environment.addStaticFunctionInterface("Vector", Vector.class);
        environment.setMeta("Vector", Vector.class);
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

    @Override
    public boolean destroy() {
        ServerSimulator server = (ServerSimulator) this.getSimulation().getSimulator("SERVER")[0];
        new ContextEvent(LogSource.here(), "Destroy", null, server, Varargs.of(getPlayer().__guid__))
                .dispatch(true);
        GUID.destroy(this);
        return true;
    }
}
