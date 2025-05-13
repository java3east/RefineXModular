package org.rs.refinex.plugin;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.ContextManager;

/**
 * A context plugin can be used to add support for a new context (game) to RefineX.
 * It tells RefineX how to interact with the context and how to run scripts in that context.
 * This class should be extended by any plugin that wants to add support for a new context.
 *
 * @author Java3east
 */
public abstract class ContextPlugin implements Plugin {
    private final String contextName;

    public ContextPlugin(final String contextName) {
        this.contextName = contextName;
        ContextManager.register(this);
    }

    @Override
    public @NotNull String getName() {
        return contextName;
    }

    @Override
    public String toString() {
        return "PLUGIN{context=" + contextName + "}";
    }
}
