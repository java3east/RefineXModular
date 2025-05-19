package org.rs.refinex.context;

import org.rs.refinex.plugin.Context;

import java.util.HashMap;

/**
 * The context manager is responsible for managing the context plugins.
 * It allows for the registration and retrieval of context plugins by their name.
 * This is useful for organizing and accessing different contexts in the application.
 *
 * @author Florian B.
 */
public class ContextManager {
    private static final HashMap<String, Context> contextPlugins = new HashMap<>();

    /**
     * Registers a new context plugin.
     * @param plugin the context plugin to register
     */
    public static void register(Context plugin) {
        contextPlugins.put(plugin.getName(), plugin);
    }

    /**
     * Returns the context plugin with the given name.
     * @param name the name of the context plugin to retrieve
     * @return the context plugin with the given name
     */
    public static Context getContext(final String name) {
        return contextPlugins.get(name);
    }
}
