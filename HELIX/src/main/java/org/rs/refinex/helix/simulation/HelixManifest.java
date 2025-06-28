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
        data.put("shared", new LimitList<>(-1));
        data.put("server", new LimitList<>(-1));
        data.put("client", new LimitList<>(-1));
    }

    @Override
    public void validate() {}

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
    public void onResourceStart(@NotNull Resource resource) { }

    @Override
    public boolean destroy() {
        return false;
    }
}
