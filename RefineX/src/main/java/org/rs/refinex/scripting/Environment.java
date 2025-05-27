package org.rs.refinex.scripting;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Function;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Environment represents a scripting environment, they are responsible for
 * loading and executing scripts.
 * Each environment is bound to a simulator.
 */
public interface Environment {
    /**
     * Adds the given namespace to the environment.
     * @param namespace the namespace to add
     */
    void addNamespace(final @NotNull Namespace namespace);

    /**
     * Loads the given string into the environment.
     * @param str the string to load
     */
    void load(final @NotNull String str);

    /**
     * Loads the given file into the environment.
     * @param path the path to the file to load
     */
    void loadfile(final @NotNull String path);

    void addEventHandler(final @NotNull ContextEventHandler handler);

    void dispatchEvent(final @NotNull ContextEvent event);

    @NotNull LogSource currentSource();

    /**
     * Returns the simulator this environment is bound to.
     * @return the simulator
     */
    @NotNull Simulator getSimulator();

    /**
     * Returns the list of namespaces in this environment.
     * @return the list of namespaces
     */
    @NotNull List<Namespace> getNamespaces();

    /**
     * Returns the path to the resource this environment is associated with.
     * @return the resource path
     */
    @NotNull String getResourcePath();

    /**
     * Returns the resource this environment is associated with.
     * @return the resource
     */
     @NotNull Resource getResource();

    /**
     * Returns the function with the given name.
     * @param name the name of the function to get
     * @return the function with the given name
     */
    default Method getFunction(final @NotNull String name) {
        for (Namespace namespace : getNamespaces()) {
            if (namespace.hasFunction(name)) return namespace.getFunction(name);
        }
        throw new RuntimeException("No function found for name " + name);
    }

    /**
     * Checks if the given function exists within this environment.
     * @param name the name of the function to check
     * @return true if the function exists, false otherwise
     */
    default boolean hasFunction(final @NotNull String name) {
        for (Namespace namespace : getNamespaces()) {
            if (namespace.hasFunction(name)) return true;
        }
        return false;
    }
}
