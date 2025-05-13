package org.rs.refinex.context;

import org.rs.refinex.plugin.ContextPlugin;

import java.util.HashMap;

/**
 * The context manager is responsible for managing the context plugins.
 * It allows for the registration and retrieval of context plugins by their name.
 * This is useful for organizing and accessing different contexts in the application.
 *
 * @author Java3east
 */
public class ContextManager {
    private static final HashMap<String, ContextPlugin> contextPlugins = new HashMap<>();

    public static void register(ContextPlugin plugin) {
        contextPlugins.put(plugin.getName(), plugin);
    }

    public static ContextPlugin getContext(final String name) {
        return contextPlugins.get(name);
    }
}
