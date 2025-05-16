package org.rs.refinex.fivem;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.fivem.namespaces.MANIFEST;
import org.rs.refinex.fivem.util.LimitList;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

import java.util.HashMap;
import java.util.List;

public class Fxmanifest extends Simulator {
    private final HashMap<String, List<String>> data = new HashMap<>() {{
        put("fx_version", new LimitList<>(1));
    }};

    public Fxmanifest(@NotNull Simulation simulation) {
        super(simulation);
    }

    public void set(@NotNull String list, @NotNull String value) {
        List<String> values = data.getOrDefault(list, new LimitList<>(-1));
        values.add(value);
        data.put(list, values);
    }

    @Override
    protected void addNamespaces(@NotNull Environment environment) {
        environment.addNamespace(new MANIFEST());
    }
}
