package org.rs.refinex.fivem.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.simulation.SimulatorManager;
import org.rs.refinex.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FiveMSimulatorManager extends SimulatorManager {
    public FiveMSimulatorManager(@NotNull Simulation simulation) {
        super(simulation);
    }

    @Override
    protected void startResource(final @NotNull Simulator simulator, final @NotNull Resource resource) {
        List<String> files = new ArrayList<>();
        String[] globs = resource.get(simulator.getType().equals("SERVER") ? "server_scripts" : "client_scripts");
        String[] sharedGlobs = resource.get("shared_scripts");
        for (String glob : sharedGlobs) {
            files.addAll(resource.getFiles(glob));
            files.addAll(resource.getFiles(glob.replace("**/", "")));
        }
        for (String glob : globs) {
            files.addAll(resource.getFiles(glob));
            files.addAll(resource.getFiles(glob.replace("**/", "")));
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
}
