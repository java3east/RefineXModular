package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.fivem.namespaces.MANIFEST;
import org.rs.refinex.util.LimitList;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;

import java.util.List;

/**
 * Represents the FiveM fxmanifest file.
 * This class is responsible for managing the fxmanifest data and validating it.
 */
public class Fxmanifest extends Manifest {
    /**
     * Creates a new Fxmanifest instance for the given simulation.
     * @param simulation the simulation to create this fxmanifest for
     */
    public Fxmanifest(@NotNull Simulation simulation) {
        super(simulation);
        data.put("fx_version", new LimitList<>(1));
        data.put("game", new LimitList<>(1));

        data.put("name", new LimitList<>(1));
        data.put("author", new LimitList<>(1));
        data.put("description", new LimitList<>(1));
        data.put("version", new LimitList<>(1));

        data.put("client_scripts", new LimitList<>(-1));
        data.put("server_scripts", new LimitList<>(-1));
        data.put("shared_scripts", new LimitList<>(-1));

        data.put("files", new LimitList<>(-1));
        data.put("ui_page", new LimitList<>(1));

        data.put("exports", new LimitList<>(-1));
        data.put("server_exports", new LimitList<>(-1));

        data.put("dependencies", new LimitList<>(-1));
        data.put("provides", new LimitList<>(-1));

        data.put("lua54", new LimitList<>(1));
        data.put("clr_disable_task_scheduler", new LimitList<>(1));
        data.put("use_fxv2_oal", new LimitList<>(1));

        data.put("escrow_ignore", new LimitList<>(-1));
        data.put("encrypted", new LimitList<>(1));

        data.put("this_is_a_map", new LimitList<>(1));
        data.put("map", new LimitList<>(1));
        data.put("loadscreen", new LimitList<>(1));

        data.put("repository", new LimitList<>(1));
        data.put("convar_category", new LimitList<>(1));
        data.put("before_level_meta", new LimitList<>(1));
    }

    @Override
    public void validate() {
        validate("fx_version", 1, 1, List.of("adamant", "cerulean", "bodacious"));
        validate("game", 1, -1, List.of("gta5", "rdr3"));
        validate("lua54", 0, 1, List.of("yes", "no"));
        validate("clr_disable_task_scheduler", 0, 1, List.of("yes", "no"));
        validate("use_fxv2_oal", 0, 1, List.of("yes", "no"));
        validate("this_is_a_map", 0, 1, List.of("yes", "no"));
        validate("map", 0, 1, List.of("yes", "no"));
        validate("loadscreen", 0, 1, List.of("yes", "no"));
        validate("encrypted", 0, 1, List.of("yes", "no"));
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new MANIFEST());
    }

    @Override
    public @NotNull String getName() {
        return "FIVEM:MANIFEST";
    }

    @Override
    public boolean onResourceStarting(@NotNull Resource resource) {
        return false;
    }

    @Override
    public void onResourceStart(@NotNull Resource resource) { }
}
