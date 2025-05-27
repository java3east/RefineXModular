package org.rs.refinex;

import org.rs.refinex.context.ContextManager;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.log.LogEntry;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.log.Logger;
import org.rs.refinex.plugin.Context;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.plugin.PluginLoader;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
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
    public static final Logger logger = new Logger();

    private static String read(File file) {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            logger.log(LogType.ERROR, "File not found: " + file.getAbsolutePath(), LogSource.here());
            return "";
        } catch (Exception e) {
            logger.log(LogType.ERROR, "Error reading file: " + file.getAbsolutePath() + " - " + e.getMessage(), LogSource.here());
            return "";
        }
    }

    public static void main(String[] args) {
        logger.log(LogType.INFO, "RefineX starting...", LogSource.here());
        if (args.length != 2) {
            logger.log(LogType.ERROR, "Usage: java -jar RefineX.jar <context_name> <runner_file>", LogSource.here());
            System.exit(1);
        }

        int amount = PluginLoader.loadAll();
        logger.log(LogType.INFO, "Loaded " + amount + " plugin(s).", LogSource.here());

        String contextName = args[0];
        String runnerName = args[1];

        Context context = ContextManager.getContext(contextName);
        if (context == null) {
            logger.log(LogType.ERROR, "Context not found: " + contextName, LogSource.here());
            System.exit(1);
        }

        File runnerFile = new File(runnerName);
        if (!runnerFile.exists()) {
            logger.log(LogType.ERROR, "Runner file not found: " + runnerName, LogSource.here());
            System.exit(1);
        }

        Simulation simulation = new Simulation(context);

        String fileExtension = runnerFile.getName().substring(runnerFile.getName().lastIndexOf(".") + 1);
        Optional<Language> language = LanguageManager.getByExtension(fileExtension);
        if (language.isEmpty()) {
            logger.log(LogType.ERROR, "No Language found to handle extension: " + fileExtension, LogSource.here());
            System.exit(1);
        }
        String content = read(runnerFile);
        Simulator simulator = simulation.manifest();
        Environment env = simulator.createEnvironment("runner", language.get(), new Resource(simulation, FileUtils.jarDirectory().getAbsolutePath()));
        env.load(content);
    }
}
