package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public abstract class Namespace {
    public boolean hasFunction(final @NotNull String name) {
        for (Method method : this.getClass().getMethods()) {
            if (method.getName().equals(name) && method.getAnnotation(Native.class) != null) {
                return true;
            }
        }
        return false;
    }

    public Method getFunction(final @NotNull String name) {
        for (Method method : this.getClass().getMethods()) {
            if (method.getName().equals(name) && method.getAnnotation(Native.class) != null) {
                return method;
            }
        }
        throw new RuntimeException("No function found for name " + name);
    }
}
