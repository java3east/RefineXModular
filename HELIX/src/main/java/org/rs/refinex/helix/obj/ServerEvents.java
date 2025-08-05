package org.rs.refinex.helix.obj;

import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.helix.HelixEventHandler;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.Varargs;

public class ServerEvents {
    @ExportFunction
    public static void SubscribeRemote(Environment environment, String event, Function callback) {
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Events.SubscribeRemote()' might no longer exist", environment.currentSource());
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
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Events.Subscribe()' might no longer exist", environment.currentSource());
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
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Events.Call()' might no longer exist", environment.currentSource());
        new ContextEvent(environment.currentSource(), event, environment, environment.getSimulator(), args)
                .dispatch(false);
    }

    @ExportFunction
    public static void CallRemote(Environment environment, String event, HPlayer player, Varargs args) {
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Events.CallRemote()' might no longer exist", environment.currentSource());
        new ContextEvent(environment.currentSource(), event, environment, player.client(), args)
                .dispatch(false);
    }

    @ExportFunction
    public static void BroadcastRemote(Environment environment, String event, Varargs args) {
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Events.BroadcastRemote()' might no longer exist", environment.currentSource());
        for (Simulator simulator : environment.getSimulator().getSimulation().getSimulator("CLIENT")) {
            new ContextEvent(environment.currentSource(), event, environment, simulator, args)
                    .dispatch(false);
        }
    }
}
