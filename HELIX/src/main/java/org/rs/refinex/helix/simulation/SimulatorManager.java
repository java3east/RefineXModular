package org.rs.refinex.helix.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.helix.simulation.simulators.ClientSimulator;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;
import org.rs.refinex.value.Varargs;

import java.util.*;

public class SimulatorManager extends org.rs.refinex.simulation.SimulatorManager {
    private int nextCLID = 1;

    public SimulatorManager(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void startResource(@NotNull Simulator simulator, @NotNull Resource resource) {
        List<String> files = new ArrayList<>();
        for (Object o : (List<String>) resource.get("shared")[0]) {
            files.addAll(resource.getFiles(o.toString()));
            files.addAll(resource.getFiles(o.toString().replace("**/", "")));
        }
        for (Object o : (List<String>) resource.get(simulator.getType().equals("SERVER") ? "server" : "client")[0]) {
            files.addAll(resource.getFiles(o.toString()));
            files.addAll(resource.getFiles(o.toString().replace("**/", "")));
        }
        HashMap<Language, List<String>> languages = new HashMap<>();
        for (String file : files) {
            Optional<Language> language = LanguageManager.getByExtension(FileUtils.getExtension(file));
            if (language.isPresent()) {
                List<String> languageFiles = languages.getOrDefault(language.get(), new ArrayList<>());
                languageFiles.add(file);
                languages.put(language.get(), languageFiles);
            }
        }
        for (Language language : languages.keySet()) {
            Environment environment = simulator.createEnvironment(language.getName() + "_" + resource.getName(), language, resource);
            for (String file : files) {
                environment.loadfile(file);
            }
        }
    }

    @Override
    protected void onCreateSimulator(@NotNull Simulator simulator) {
        if (simulator.getType().equals("SERVER")) {
            simulator.setData("server_id", 0);
        } else {
            simulator.setData("server_id", nextCLID++);
            Simulator server = simulator.getSimulation().getSimulator("SERVER")[0];
            simulator.setData("server", server);
            ClientSimulator clientSimulator = (ClientSimulator) simulator;
            new ContextEvent(LogSource.here(), "Spawn", null, server, Varargs.of(clientSimulator.getPlayer().__guid__))
                    .dispatch(true);
        }
    }
}
