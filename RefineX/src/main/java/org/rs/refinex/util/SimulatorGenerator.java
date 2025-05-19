package org.rs.refinex.util;

import org.rs.refinex.simulation.Simulation;

/**
 * Interface for generating simulator objects.
 * This is used to create simulator objects for different contexts.
 */
public interface SimulatorGenerator {
    /**
     * Generates a simulator object for the given simulation.
     * @param simulation the simulation to generate the simulator for
     * @return the generated simulator object
     */
    Object generate(Simulation simulation);
}
