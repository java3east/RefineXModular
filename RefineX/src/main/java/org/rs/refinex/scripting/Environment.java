package org.rs.refinex.scripting;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Function;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * Environment represents a scripting environment, they are responsible for
 * loading and executing scripts.
 * Each environment is bound to a simulator.
 */
public interface Environment {
    /**
     * Returns the value from the data store with the given key.
     * @param key the key to get the value for
     * @return an Optional containing the value if it exists, or an empty Optional if it does not
     */
    Optional<Object> get(final @NotNull String key);

    /**
     * Returns the value of the global variable with the given key.
     * @param key the key to get the value for
     * @return the value of the global variable, or null if it does not exist
     */
    Object getGlobal(final @NotNull String key);

    /**
     * Sets the value for the given key in the data store.
     * @param key the key to set the value for
     * @param value the value to set
     * @param shared if true this value will also be pushed to the scripting environment
     */
    void set(final @NotNull String key, final @NotNull Object value, boolean shared);

    /**
     * Adds the given namespace to the environment.
     * @param namespace the namespace to add
     */
    void addNamespace(final @NotNull Namespace namespace);

    void callSrcFunction(final @NotNull String name, Object... args);

    default void addStaticFunctionInterface(String name, final @NotNull Class<?> clazz) {
        Object val = getLanguage().getValueMapper().staticFunctionInterface(clazz, this);
        set(name, val, true);
    }

    default void setMeta(String objName, final @NotNull Class<?> metaClass) {
        Object val = getLanguage().getValueMapper().staticFunctionInterface(metaClass, this);
        Object o = getGlobal(objName);
        if (o == null) {
            throw new RuntimeException("No object found for name " + objName);
        }
        this.callSrcFunction("setmetatable", o, val);
    }

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

    long functionReference(final @NotNull Function function);

    Function getFunctionReference(final long refId);

    Object envTypeFunctionalObject(Object obj, boolean isStatic);

    void addEventHandler(final @NotNull ContextEventHandler handler);

    boolean dispatchEvent(final @NotNull ContextEvent event);

    void tick(double frameTime);

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

     Language getLanguage();

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
