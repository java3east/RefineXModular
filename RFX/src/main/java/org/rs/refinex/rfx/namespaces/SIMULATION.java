package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextManager;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;

/**
 * Namespace for simulation-related functions.
 */
public class SIMULATION extends Namespace {
    @Native
    public static GUID SIMULATION_CREATE(Environment env, String contextName) {
        Context context = ContextManager.getContext(contextName);
        Simulation simulation = context.createSimulation();
        return GUID.register(simulation);
    }

    @Native
    public static GUID SIMULATION_GET_SERVER(Environment env, GUID simulationId) {
        Simulation simulation = (Simulation) GUID.get(simulationId, Simulation.class);
        return GUID.register(simulation.getSimulator("SERVER")[0]);
    }
}
