package org.rs.refinex.json;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.plugin.RefineXPlugin;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.ValueMapper;

@RefineXPlugin
public class JSON extends Language {
    public JSON() {
        super("JSON");
    }

    @Override
    public @NotNull String getExtension() {
        return "json";
    }

    @Override
    public @NotNull Environment createEnvironment(@NotNull Simulator simulator, @NotNull Resource resource) {
        return new JsonEnvironment(simulator, resource);
    }

    @Override
    public @NotNull ValueMapper<?> getValueMapper() {
        return new JsonValueMapper();
    }

    @Override
    public void onLoad() { }
}
