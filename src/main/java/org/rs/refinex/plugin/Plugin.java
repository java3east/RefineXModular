package org.rs.refinex.plugin;

import org.jetbrains.annotations.NotNull;

/**
 * All types of plugins should implement this interface.
 * RefineX will interact with all plugins through this interface.
 *
 * @author Java3east
 */
public interface Plugin {
    /**
     * This method will be called when the plugin is loaded by
     * the RefineX plugin loader. This is where you should initialize
     * your plugin
     */
    void onLoad();

    /**
     * This method should return the name of the plugin. E.g. the name of the
     * language or context that the plugin supports.
     * @return The name of the plugin.
     */
    @NotNull String getName();
}
