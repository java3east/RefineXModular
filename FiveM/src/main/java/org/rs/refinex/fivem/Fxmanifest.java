package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.fivem.namespaces.MANIFEST;
import org.rs.refinex.fivem.util.LimitList;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;

import java.util.HashMap;
import java.util.List;

/**
 * Represents the FiveM fxmanifest file.
 * This class is responsible for managing the fxmanifest data and validating it.
 */
public class Fxmanifest extends Manifest {
    private final HashMap<String, List<String>> data = new HashMap<>() {{
        put("fx_version", new LimitList<>(1));
        put("game", new LimitList<>(1));

        put("name", new LimitList<>(1));
        put("author", new LimitList<>(1));
        put("description", new LimitList<>(1));
        put("version", new LimitList<>(1));

        put("client_scripts", new LimitList<>(-1));
        put("server_scripts", new LimitList<>(-1));
        put("shared_scripts", new LimitList<>(-1));

        put("files", new LimitList<>(-1));
        put("ui_page", new LimitList<>(1));

        put("exports", new LimitList<>(-1));
        put("server_exports", new LimitList<>(-1));

        put("dependencies", new LimitList<>(-1));
        put("provides", new LimitList<>(-1));

        put("lua54", new LimitList<>(1));
        put("clr_disable_task_scheduler", new LimitList<>(1));
        put("use_fxv2_oal", new LimitList<>(1));

        put("escrow_ignore", new LimitList<>(-1));
        put("encrypted", new LimitList<>(1));

        put("this_is_a_map", new LimitList<>(1));
        put("map", new LimitList<>(1));
        put("loadscreen", new LimitList<>(1));

        put("repository", new LimitList<>(1));
        put("convar_category", new LimitList<>(1));
        put("before_level_meta", new LimitList<>(1));
    }};

    /**
     * Creates a new Fxmanifest instance for the given simulation.
     * @param simulation the simulation to create this fxmanifest for
     */
    public Fxmanifest(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    public void set(@NotNull String list, @NotNull String value) {
        List<String> values = data.getOrDefault(list, new LimitList<>(-1));
        values.add(value);
        data.put(list, values);
    }

    @Override
    public String[] get(@NotNull String key) {
        return data.getOrDefault(key, new LimitList<>(-1)).toArray(new String[0]);
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
    public void onResourceStart(@NotNull Resource resource) { }
}
