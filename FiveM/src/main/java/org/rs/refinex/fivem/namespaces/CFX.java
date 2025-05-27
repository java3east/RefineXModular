package org.rs.refinex.fivem.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.fivem.FiveMEventHandler;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Function;


public class CFX extends Namespace {
    @Native
    public static String GetCurrentResourceName(Environment environment) {
        return environment.getResource().getName();
    }

    @Native
    public static void AddEventHandler(Environment environment, String name, Function function) {
        environment.addEventHandler(new FiveMEventHandler(name, function, false, environment.currentSource()));
    }
}
