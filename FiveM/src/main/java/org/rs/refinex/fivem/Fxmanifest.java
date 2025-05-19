package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Manifest;
import org.rs.refinex.fivem.namespaces.MANIFEST;
import org.rs.refinex.fivem.util.LimitList;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;

import java.util.HashMap;
import java.util.List;

public class Fxmanifest extends Manifest {
    private final HashMap<String, List<String>> data = new HashMap<>() {{
        put("fx_version", new LimitList<>(1));
        put("name", new LimitList<>(1));
    }};

    public Fxmanifest(@NotNull Simulation simulation) {
        super(simulation);
    }

    private void validateFxVersion() {
        List<String> fxVersions = data.get("fx_version");
        if (fxVersions == null || fxVersions.isEmpty()) {
            throw new IllegalStateException("fx_version not set");
        }
        String fxVersion = fxVersions.getFirst();
        if (!(fxVersion.equals("cerulean") || fxVersion.equals("adamant") || fxVersion.equals("bodacious") || fxVersion.equals("beyond")))
            throw new IllegalStateException("Invalid fx version: " + fxVersion);
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
        validateFxVersion();
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new MANIFEST());
    }
}
