package org.rs.refinex.util;

import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public interface SimulatorGenerator {
    Object generate(Simulation simulation);
}
