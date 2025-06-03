package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.event.EventDispatchedEvent;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Varargs;

public record ContextEvent (@NotNull LogSource origin, @NotNull String name, Environment source, @NotNull Simulator target, @NotNull
                            Varargs data) {
    public void dispatch(boolean ignoreMissingHandlers) {
        RefineX.manager.dispatchEvent(new EventDispatchedEvent(this, false));
        target.dispatchEvent(this, ignoreMissingHandlers);
    }

    public void queue() {
        RefineX.manager.dispatchEvent(new EventDispatchedEvent(this, true));
        target.queueEvent(this);
    }
}
