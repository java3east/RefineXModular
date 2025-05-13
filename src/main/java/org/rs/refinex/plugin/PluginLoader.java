package org.rs.refinex.plugin;

import org.rs.refinex.RefineX;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Scanner;

/**
 * The plugin loader is responsible for loading .jar files as plugins from the file system.
 * Plugins should be placed in the ./plugins directory and each plugin should have its own directory, including
 * a plugin.txt file that specifies the path to the main class of the plugin and a plugin.jar file which
 * contains the compiled plugin code.
 *
 * @author Java3east
 */
public class PluginLoader {
    private static String read(final File file) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * Loads the plugin with the specified name. The plugin name is the name of the directory the plugin is in.
     * @param name the name of the plugin to load
     */
    public static void load(final String name) {
        try {
            File jarFile = new File("./plugins/" + name + "/plugin.jar");
            URL jarUrl = jarFile.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl}, RefineX.class.getClassLoader());

            File pluginTxt = new File("./plugins/" + name + "/plugin.txt");
            String path = read(pluginTxt).trim();

            Class<?> loadedClass = loader.loadClass(path);

            // Optionally, invoke a method
            Method method = loadedClass.getMethod("onLoad");
            Plugin plugin = (Plugin) loadedClass.getDeclaredConstructor().newInstance();
            method.invoke(plugin);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads all plugins in the ./plugins directory. Each plugin should be in its own directory
     * and should contain a plugin.txt file that specifies the path to the main class of the plugin.
     * The plugin.jar file should be in the same directory as the plugin.txt file.
     */
    public static void loadAll() {
        File pluginsDir = new File("./plugins");
        if (pluginsDir.exists() && pluginsDir.isDirectory()) {
            for (File file : Objects.requireNonNull(pluginsDir.listFiles())) {
                if (file.isDirectory()) {
                    File pluginTxt = new File(file, "plugin.txt");
                    if (pluginTxt.exists()) {
                        String name = file.getName();
                        load(name);
                    } else {
                        System.out.println("No plugin.txt found in " + file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("Plugins directory does not exist.");
        }
    }
}
