package org.rs.refinex.helix.simulation;

import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.util.LimitList;

import java.util.List;

public class HelixManifest extends Manifest {
    /**
     * Creates a new instance of the manifest.
     *
     * @param simulation the simulation this manifest belongs to
     */
    public HelixManifest(@NotNull Simulation simulation) {
        super(simulation);
        data.put("title", new LimitList<>(1));
        data.put("author", new LimitList<>(1));
        data.put("version", new LimitList<>(1));
        data.put("force_no_map_package", new LimitList<>(1));
        data.put("auto_cleanup", new LimitList<>(1));
        data.put("load_level_entities", new LimitList<>(1));
        data.put("compatibility_version", new LimitList<>(1));
        data.put("packages_requirements", new LimitList<>(-1));
        data.put("assets_requirement", new LimitList<>(-1));
        data.put("compatible_game_modes", new LimitList<>(-1));
    }

    @Override
    public void validate() {
        validate("title", 1, 1, List.of());
        validate("author", 1, 1, List.of());
        validate("version", 1, 1, List.of());
        validate("force_no_map_package", 1, 1, List.of(true, false));
        validate("auto_cleanup", 1, 1, List.of(true, false));
        validate("load_level_entities", 1, 1, List.of(true, false));
        validate("compatibility_version", 1, 1, List.of());
        validate("packages_requirements", 0, -1, List.of());
        validate("assets_requirement", 0, -1, List.of());
        validate("compatible_game_modes", 0, -1, List.of());
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) { }

    @Override
    public @NotNull String getName() {
        return "HELIX:MANIFEST";
    }

    @Override
    public boolean onResourceStarting(@NotNull Resource resource) {
        return false;
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) {

    }
}
