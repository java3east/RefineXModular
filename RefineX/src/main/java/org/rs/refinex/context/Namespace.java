package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * A namespace contains {@link Native} functions that can be called from the scripting engine.
 */
public abstract class Namespace {
    /**
     * Checks if the given function exits within this namespace.
     * @param name the name of the function to check
     * @return true if the function exists, false otherwise
     */
    public boolean hasFunction(final @NotNull String name) {
        for (Method method : this.getClass().getMethods()) {
            if (method.getName().equals(name) && method.getAnnotation(Native.class) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the function with the given name.
     * @param name the name of the function to get
     * @return the function with the given name
     */
    public Method getFunction(final @NotNull String name) {
        for (Method method : this.getClass().getMethods()) {
            if (method.getName().equals(name) && method.getAnnotation(Native.class) != null) {
                return method;
            }
        }
        throw new RuntimeException("No function found for name " + name);
    }
}
