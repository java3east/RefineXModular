package org.rs.refinex.scripting;

import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.util.FileUtils;

import java.util.HashMap;
import java.util.List;

/**
 * A resource manager is responsible for loading and managing resources.
 *
 */
public class ResourceManager {
    private final Simulation simulation;
    private final HashMap<String, Resource> resources = new HashMap<>();

    /**
     * Creates a new resource manager for the given simulation.
     * @param simulation the simulation this resource manager will be used for
     */
    public ResourceManager(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Loads a resource from the given path.
     * @param path the path to the resource folder
     * @return the loaded resource
     */
    public Resource load(String path) {
        List<String> resourcePaths = List.of(
                path, FileUtils.currentDirectory().getPath() + path,
                FileUtils.jarDirectory().getPath() + path
        );

        String manifestName = simulation.getContext().manifestName();
        for (String p : resourcePaths) {
            if (FileUtils.exists(p + "/" + manifestName)) {
                Resource resource = new Resource(simulation, p);
                resource.load();
                this.resources.put(resource.getName(), resource);
                return resource;
            }
        }
        throw new RuntimeException("Could not find resource: " + path);
    }

    /**
     * Returns the resource with the given name.
     * @return the resource with the given name
     */
    public Resource get(String name) {
        return resources.get(name);
    }
}
