package org.rs.refinex.fivem.namespaces;

import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.fivem.FiveMEventHandler;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.Varargs;


public class CFX extends Namespace {
    @Native
    public static String GetCurrentResourceName(Environment environment) {
        return environment.getResource().getName();
    }

    @Native
    public static void AddEventHandler(Environment environment, String name, Function function) {
        environment.addEventHandler(new FiveMEventHandler(environment, name, function, false, environment.currentSource()));
    }

    @Native
    public static void RegisterNetEvent(Environment environment, String name, Function function) {
        environment.addEventHandler(new FiveMEventHandler(environment, name, function, true, environment.currentSource()));
    }

    @Native
    public static void TriggerServerEvent(Environment environment, String name, Varargs args) {
        new ContextEvent(environment.currentSource(), name, environment, environment.getSimulator().getSimulation()
                .getSimulator("SERVER")[0], args).dispatch();
    }
}
