package org.rs.refinex.scripting;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.simulation.Simulator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Environment represents a scripting environment, they are responsible for
 * loading and executing scripts.
 * Each environment is bound to a simulator.
 */
public interface Environment {
    void addNamespace(final @NotNull Namespace namespace);
    void load(final @NotNull String str);
    void loadfile(final @NotNull String path);
    @NotNull Simulator getSimulator();
    @NotNull List<Namespace> getNamespaces();

    default Method getFunction(final @NotNull String name) {
        for (Namespace namespace : getNamespaces()) {
            if (namespace.hasFunction(name)) return namespace.getFunction(name);
        }
        throw new RuntimeException("No function found for name " + name);
    }

    default boolean hasFunction(final @NotNull String name) {
        for (Namespace namespace : getNamespaces()) {
            if (namespace.hasFunction(name)) return true;
        }
        return false;
    }
}
