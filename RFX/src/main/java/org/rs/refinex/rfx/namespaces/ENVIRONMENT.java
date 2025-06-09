package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Varargs;

import java.util.Optional;

public class ENVIRONMENT extends Namespace {
    @Native
    public static GUID ENVIRONMENT_GET(Environment environment, GUID simulatorId, GUID resourceId) {
        Simulator simulator = (Simulator) GUID.get(simulatorId, Simulator.class);
        Resource resource = (Resource) GUID.get(resourceId, Resource.class);
        Environment env = simulator.getEnvironment("LUA_" + resource.getName());
        return GUID.register(env);
    }

    @Native
    public static Object ENVIRONMENT_GET_VAR(Environment environment, GUID envID, String key) {
        Environment env = (Environment) GUID.get(envID, Environment.class);
        return env.getGlobal(key);
    }
}
