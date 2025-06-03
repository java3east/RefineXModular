package org.rs.refinex.fivem.namespaces;

import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.fivem.FiveMEventHandler;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.Varargs;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


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
                .getSimulator("SERVER")[0], args).dispatch(false);
    }

    @Native
    public static void CREATE_EXPORT(Environment environment, String name, Function function) {
        Optional<Object> exports = environment.getSimulator().getData("exports_" + environment.getResource().getName());
        if (exports.isEmpty() || !(exports.get() instanceof Map)) {
            environment.getSimulator().setData("exports_" + environment.getResource().getName(), new HashMap<String, Function>());
            exports = environment.getSimulator().getData("exports_" + environment.getResource().getName());
        }
        Map<String, Function> exportMap = (Map<String, Function>) exports.get();
        exportMap.put(name, function);
    }

    @Native
    public static Function GET_EXPORT(Environment environment, String name, String fnName) {
        Optional<Object> exports = environment.getSimulator().getData("exports_" + name);
        if (exports.isEmpty() || !(exports.get() instanceof Map)) {
            return null;
        }
        Map<String, Function> exportMap = (Map<String, Function>) exports.get();
        return exportMap.get(fnName);
    }
}
