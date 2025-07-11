package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

/**
 * Contains native simulator related functions.
 */
public class SIMULATOR extends Namespace {
    @Native
    public static GUID SIMULATOR_CREATE(Environment environment, GUID simulationID, String simulatorType) {
        Simulation sim = (Simulation) GUID.get(simulationID, Simulation.class);
        Simulator simulator = sim.createSimulator(simulatorType);
        return GUID.register(simulator);
    }

    @Native
    public static void SIMULATOR_TICK(Environment environment, GUID simulatorID, long ms) {
        Simulator sim = (Simulator) GUID.get(simulatorID, Simulator.class);
        sim.tick(ms / 1000.0);
    }

    @Native
    public static void SIMULATOR_DESTROY(Environment environment, GUID simulatorID) {
        Simulator sim = (Simulator) GUID.get(simulatorID, Simulator.class);
        sim.destroy();
    }
}
