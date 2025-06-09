package org.rs.refinex.toml;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.plugin.RefineXPlugin;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.ValueMapper;

@RefineXPlugin
public class TOML extends Language {
    public TOML() {
        super("TOML");
    }

    @Override
    public @NotNull String getExtension() {
        return "toml";
    }

    @Override
    public @NotNull Environment createEnvironment(@NotNull Simulator simulator, @NotNull Resource resource) {
        return new TomlEnvironment(simulator, resource);
    }

    @Override
    public @NotNull ValueMapper<?> getValueMapper() {
        return new TomlValueMapper();
    }

    @Override
    public void onLoad() { }
}
