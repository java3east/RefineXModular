package org.rs.refinex.helix.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public class SimulatorManager extends org.rs.refinex.simulation.SimulatorManager {
    private int nextCLID = 1;

    public SimulatorManager(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    protected void startResource(@NotNull Simulator simulator, @NotNull Resource resource) {
        Language language = LanguageManager.getLanguage("LUA");
        Environment environment = simulator.createEnvironment("LUA_" + resource.getName(), language, resource);
        String glob;
        if (simulator.getType().equals("SERVER"))
            glob = "Server/Index.lua";
        else
            glob = "Client/Index.lua";

        for (String file : resource.getFiles("Shared/Index.lua")) {
            environment.loadfile(file);
        }

        for (String file : resource.getFiles(glob)) {
            environment.loadfile(file);
        }
    }

    @Override
    protected void onCreateSimulator(@NotNull Simulator simulator) {
        if (simulator.getType().equals("SERVER")) {
            simulator.setData("server_id", 0);
        } else {
            simulator.setData("server_id", nextCLID++);
        }
    }
}
