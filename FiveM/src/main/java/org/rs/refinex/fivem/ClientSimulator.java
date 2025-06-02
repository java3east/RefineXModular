package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.fivem.namespaces.CFX;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Varargs;

import java.util.Optional;

public class ClientSimulator extends Simulator {
    public ClientSimulator(@NotNull Simulation simulation) {
        super(simulation, "CLIENT");
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new CFX());
    }

    @Override
    public @NotNull String getName() {
        return "CLIENT:" + GUID.register(this).guid;
    }

    @Override
    public boolean onResourceStarting(@NotNull Resource resource) {
        setData("event_canceled", false);
        new ContextEvent(LogSource.here(), "onResourceStarting", null, this, Varargs.of(resource.getName())).dispatch();
        Optional<Object> cancel = getData("event_canceled");
        return cancel.filter(o -> (boolean) o).isPresent();
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) {
        new ContextEvent(LogSource.here(), "onClientResourceStart", null, this, Varargs.of(resource.getName())).queue();
        new ContextEvent(LogSource.here(), "onResourceStart", null, this, Varargs.of(resource.getName())).dispatch();
    }
}
