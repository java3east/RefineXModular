package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.fivem.namespaces.MANIFEST;
import org.rs.refinex.fivem.util.LimitList;
import org.rs.refinex.scripting.Environment;
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
        put("name", new LimitList<>(1));
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
        validate(get("fx_version"), 1, 1, List.of("adamant", "cerulean", "bodacious"));
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new MANIFEST());
    }
}
