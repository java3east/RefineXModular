package org.rs.refinex;

import org.rs.refinex.context.ContextManager;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.plugin.PluginLoader;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

/**
 * RefineX is a plugin-based framework designed to run and test LUA scripts for various games.
 * It provides a flexible and extensible architecture that allows developers to create plugins
 * which can be loaded to support different games and features.
 *
 * @author Florian B.
 */
public class RefineX {
    private static String read(File file) {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: refinex <CONTEXT> <RUNNER>");
            System.exit(1);
        }

        System.out.println(">>> Loading plugins");
        PluginLoader.loadAll();
        System.out.println(">>> Plugins loaded");

        String contextName = args[0];
        String runnerName = args[1];

        Context context = ContextManager.getContext(contextName);
        if (context == null) {
            System.out.println("Context not found: " + contextName);
            System.exit(1);
        }

        File runnerFile = new File(runnerName);
        if (!runnerFile.exists()) {
            System.out.println("Runner file not found: " + runnerFile.getAbsolutePath());
            System.exit(1);
        }

        Simulation simulation = new Simulation(context);

        String fileExtension = runnerFile.getName().substring(runnerFile.getName().lastIndexOf(".") + 1);
        Optional<Language> language = LanguageManager.getByExtension(fileExtension);
        if (language.isEmpty()) {
            System.out.println("No Language found to handle extension: " + fileExtension);
            System.exit(1);
        }
        String content = read(runnerFile);
        Simulator simulator = simulation.manifest();
        Environment env = simulator.createEnvironment("runner", language.get());
        env.load(content);
    }
}
