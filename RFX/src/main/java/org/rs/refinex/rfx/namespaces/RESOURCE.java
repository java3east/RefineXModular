package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.RefineX;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;

/**
 * Namespace for resource related native functions.
 */
public class RESOURCE extends Namespace {
    @Native
    public static GUID RESOURCE_LOAD(Environment environment, GUID simulationID, String path) {
        Simulation sim = (Simulation) GUID.get(simulationID, Simulation.class);
        Resource resource = sim.load(path);
        RefineX.logger.log(LogType.INFO, "Resource '" + resource.getName() + "' refreshed.", LogSource.here());
        return GUID.register(resource);
    }

    @Native
    public static void RESOURCE_START(Environment environment, GUID resourceID) {
        Resource resource = (Resource) GUID.get(resourceID, Resource.class);
        resource.start();
        RefineX.logger.log(LogType.INFO, "Resource '" + resource.getName() + "' started.", LogSource.here());
    }
}
