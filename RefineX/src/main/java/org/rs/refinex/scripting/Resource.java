package org.rs.refinex.scripting;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.util.FileUtils;

import java.util.List;
import java.util.Optional;

public class Resource {
    /**
     * The simulation this resource is loaded for.
     */
    private final Simulation simulation;

    /**
     * The path to the resource folder
     */
    private final String path;

    /**
     * The manifest of the resource.
     */
    private Manifest manifest;

    /**
     * Creates a new resource for the given simulation and path.
     * @param simulation the simulation this resource will be loaded for
     * @param path the path to the resource folder
     */
    public Resource(final @NotNull Simulation simulation, final @NotNull String path) {
        this.simulation = simulation;
        this.path = path;
    }

    public void load() {
        manifest = simulation.defaultSimulator();
        String extension = FileUtils.getExtension(path + "/" + simulation.getContext().manifestName());
        Optional<Language> language = LanguageManager.getByExtension(extension);
        if (language.isEmpty())
            throw new RuntimeException("No language found for extension: " + extension);
        Language lang = language.get();
        Environment environment = manifest.createEnvironment("manifest", lang);
        environment.loadfile(path + "/" + simulation.getContext().manifestName());
        manifest.validate();
        System.out.println("Resource '" + getName() + "' refreshed.");
    }

    public String[] get(@NotNull String key) {
        return manifest.get(key);
    }

    public String get(@NotNull String key, int index) {
        if (index >= manifest.get(key).length)
            return null;
        return manifest.get(key)[index];
    }

    public String getName() {
        String name = get("name", 0);
        if (name == null)
            name = path.substring(path.lastIndexOf("/") + 1);
        return name;
    }
}
