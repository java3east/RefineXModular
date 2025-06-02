package org.rs.refinex.event;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private final List<Class<?>> handlers = new ArrayList<>();

    public void registerEventHandler(Class<?> handler) {
        handlers.add(handler);
    }

    public void dispatchEvent(Event event) {
        for (Class<?> handler : handlers) {
            try {
                var methods = handler.getDeclaredMethods();
                for (var method : methods) {
                    if (method.isAnnotationPresent(EventHandler.class)) {
                        if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == event.getClass()) {
                            method.setAccessible(true);
                            method.invoke(handler.getDeclaredConstructor().newInstance(), event);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
