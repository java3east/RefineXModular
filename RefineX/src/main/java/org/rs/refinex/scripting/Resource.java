package org.rs.refinex.scripting;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.util.Cache;
import org.rs.refinex.util.FileUtils;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private boolean running = false;

    private final Cache<List<Path>> fileCache = new Cache<>();

    /**
     * Creates a new resource for the given simulation and path.
     * @param simulation the simulation this resource will be loaded for
     * @param path the path to the resource folder
     */
    public Resource(final @NotNull Simulation simulation, final @NotNull String path) {
        this.simulation = simulation;
        this.path = path;
    }

    /**
     * Loads (refreshes) the resource.
     * This will load the manifest file and validate it.
     */
    public void load() {
        manifest = simulation.manifest();
        String extension = FileUtils.getExtension(path + "/" + simulation.getContext().manifestName());
        Optional<Language> language = LanguageManager.getByExtension(extension);
        if (language.isEmpty())
            throw new RuntimeException("No language found for extension: " + extension);
        Language lang = language.get();
        Environment environment = manifest.createEnvironment("manifest", lang);
        environment.loadfile(path + "/" + simulation.getContext().manifestName());
        manifest.validate();
    }

    /**
     * Starts this resource.
     */
    public void start() {
        this.running = true;
        this.simulation.startResource(this);
    }

    /**
     * Stops this resource.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Checks if this resource is running.
     * @return true if this resource is running, false otherwise
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Returns the values for the given key in the manifest.
     * @param key the key to get the values for
     * @return the values for the given key
     */
    public String[] get(@NotNull String key) {
        return manifest.get(key);
    }

    /**
     * Returns the value for the given key at the given index.
     * @param key the key to get the value for
     * @param index the index to get the value for
     * @return the value for the given key at the given index
     */
    public String get(@NotNull String key, int index) {
        if (index >= manifest.get(key).length)
            return null;
        return manifest.get(key)[index];
    }

    /**
     * Returns the name of this resource.
     * If the name is not set in the manifest, the name will be
     * the last part of the path.
     * @return the name of this resource
     */
    public String getName() {
        String name = get("name", 0);
        if (name == null)
            name = path.substring(path.lastIndexOf("/") + 1);
        return name;
    }

    private @NotNull List<Path> getFiles() {
        return fileCache.get("", () ->
                FileUtils.getFilesRecursive(new File(path))
        );
    }

    private @NotNull List<Path> getFiles(final @NotNull PathMatcher matcher) {
        List<Path> files = getFiles();
        return files.stream().filter(matcher::matches).collect(Collectors.toList());
    }

    public @NotNull List<String> getFiles(@NotNull String glob) {
        glob = new File(this.path).getAbsolutePath() + "/" + glob;
        @NotNull String finalGlob = glob;
        return fileCache.get(glob, () ->
            getFiles(FileSystems.getDefault().getPathMatcher("glob:" + finalGlob))
        ).stream().map(Path::toString).collect(Collectors.toList());
    }
}
