package org.rs.refinex.helix.obj;

import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.helix.HelixEventHandler;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.Varargs;

public class ClientEvents {
    @ExportFunction
    public static void SubscribeRemote(Environment environment, String event, Function callback) {
        environment.addEventHandler(
                new HelixEventHandler(
                        environment,
                        event,
                        callback,
                        true,
                        environment.currentSource()
                )
        );
    }

    @ExportFunction
    public static void Subscribe(Environment environment, String event, Function callback) {
        environment.addEventHandler(
                new HelixEventHandler(
                        environment,
                        event,
                        callback,
                        false,
                        environment.currentSource()
                )
        );
    }

    @ExportFunction
    public static void Call(Environment environment, String event, Varargs args) {
        new ContextEvent(environment.currentSource(), event, environment, environment.getSimulator(), args)
                .dispatch(false);
    }

    @ExportFunction
    public static void CallRemote(Environment environment, String event, Varargs args) {
        Simulator server = environment.getSimulator().getSimulation().getSimulator("SERVER")[0];
        new ContextEvent(environment.currentSource(), event, environment, server, args)
                .dispatch(false);
    }
}
