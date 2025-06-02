package org.rs.refinex.event;

import org.rs.refinex.context.ContextEvent;

public class EventDispatchedEvent extends Event {
    private final ContextEvent event;
    private final boolean queue;

    public EventDispatchedEvent(ContextEvent event, boolean queue) {
        super("event_dispatched");
        this.event = event;
        this.queue = queue;
    }

    public ContextEvent getEvent() {
        return event;
    }

    public boolean gotQueued() {
        return queue;
    }
}
