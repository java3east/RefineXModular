package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.context.ContextManager;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class SIMULATION extends Namespace {
    @Native
    public static GUID SIMULATION_CREATE(Environment env, String contextName) {
        Context context = ContextManager.getContext(contextName);
        Simulation simulation = new Simulation(context);
        return GUID.register(simulation);
    }

    @Native
    public static GUID SIMULATION_CREATE_SIMULATOR(Environment env, GUID simulation, String simulatorType) {
        Simulation sim = (Simulation) GUID.get(simulation, Simulation.class);
        Simulator simulator = sim.createSimulator(simulatorType);
        return GUID.register(simulator);
    }

    @Native
    public static GUID SIMULATION_LOAD_RESOURCE(Environment env, GUID simulation, String path) {
        Simulation sim = (Simulation) GUID.get(simulation, Simulation.class);
        Resource resource = sim.load(path);
        return GUID.register(resource);
    }
}
