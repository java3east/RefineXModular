package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.context.ContextManager;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;

import java.util.List;
import java.util.Optional;

public class SIMULATION extends Namespace {
    @Native
    public static GUID SIMULATION_CREATE(Environment env, String contextName) {
        Context context = ContextManager.getContext(contextName);
        Simulation simulation = new Simulation(context);
        return GUID.register(simulation);
    }

    @Native
    public static GUID SIMULATION_CREATE_SIMULATOR(Environment env, GUID simulation, String simulatorType) {
        Simulation sim = (Simulation) GUID.get(simulation, Simulation.class);
        Simulator simulator = sim.createSimulator(simulatorType);
        return GUID.register(simulator);
    }

    @Native
    public static GUID SIMULATION_LOAD_RESOURCE(Environment env, GUID simulation, String path) {
        List<String> paths = List.of(
                path, FileUtils.currentDirectory().getPath() + path,
                FileUtils.jarDirectory().getPath() + path
        );

        Simulation sim = (Simulation) GUID.get(simulation, Simulation.class);

        String manifestName = sim.getContext().manifestName();
        for (String p : paths) {
            if (FileUtils.exists(p + "/" + manifestName)) {
                Simulator simulator = sim.defaultSimulator();
                String extension = FileUtils.getExtension(manifestName);
                Optional<Language> language = LanguageManager.getByExtension(extension);
                if (language.isEmpty())
                    throw new RuntimeException("No language found for extension: " + extension);
                Language lang = language.get();
                Environment environment = simulator.createEnvironment("manifest", lang);
                environment.loadfile(p + "/" + manifestName);
                return GUID.register(environment);
            }
        }

        throw new RuntimeException("Could not find resource: " + path);
    }
}
