package org.rs.refinex;

import org.rs.refinex.plugin.PluginLoader;

/**
 * RefineX is a plugin-based framework designed to run and test LUA scripts for various games.
 * It provides a flexible and extensible architecture that allows developers to create plugins
 * which can be loaded to support different games and features.
 *
 * @author Florian B.
 */
public class RefineX {
    public static void main(String[] args) {
        System.out.println(">>> Loading plugins");
        PluginLoader.loadAll();
        System.out.println(">>> Plugins loaded");
    }
}
