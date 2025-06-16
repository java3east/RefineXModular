package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.event.EventDispatchedEvent;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.Varargs;

import java.util.HashMap;

public class ContextEvent {
    private final HashMap<String, Object> addData = new HashMap<>();
    private final LogSource origin;
    private final String name;
    private final Environment source;
    private final Simulator target;
    private final Varargs data;

    public ContextEvent (@NotNull LogSource origin, @NotNull String name, Environment source, @NotNull Simulator target, @NotNull
        Varargs data) {
        this.origin = origin;
        this.name = name;
        this.source = source;
        this.target = target;
        this.data = data;
    }

    public ContextEvent set(String key, Object value) {
        this.addData.put(key, value);
        return this;
    }

    public Object get(String key) {
        return this.addData.get(key);
    }

    public LogSource origin() {
        return this.origin;
    }

    public String name() {
        return this.name;
    }

    public Environment source() {
        return this.source;
    }

    public Simulator target() {
        return this.target;
    }

    public Varargs data() {
        return this.data;
    }

    public void dispatch(boolean ignoreMissingHandlers) {
        RefineX.manager.dispatchEvent(new EventDispatchedEvent(this, false));
        target.dispatchEvent(this, ignoreMissingHandlers);
    }

    public void queue() {
        RefineX.manager.dispatchEvent(new EventDispatchedEvent(this, true));
        target.queueEvent(this);
    }
}
