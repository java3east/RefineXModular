package org.rs.refinex.fivem.quality;

import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextManager;
import org.rs.refinex.event.EventDispatchedEvent;
import org.rs.refinex.event.EventHandler;
import org.rs.refinex.event.NativeCallEvent;
import org.rs.refinex.log.LogType;
import org.rs.refinex.simulation.Simulator;

import java.util.ArrayList;
import java.util.List;

public class NFCOL {
    private static final List<Simulator> started = new ArrayList<>();
    private static final List<String> startEventNames = List.of(
            "onClientResourceStart",
            "onServerResourceStart",
            "onResourceStart"
    );
    private static final List<String> allowedNatives = List.of(
        "AddEventHandler",
        "RegisterNetEvent",
        "CREATE_EXPORT"
    );

    @EventHandler
    public static void onEvent(EventDispatchedEvent event) {
        for (String eventName : startEventNames) {
            if (event.getEvent().name().equals(eventName)) {
                Simulator simulator = event.getEvent().target();
                started.add(simulator);
            }
        }
    }

    @EventHandler
    public static void onNative(NativeCallEvent event) {
        if (event.getEnvironment().getSimulator().getSimulation().getContext() != ContextManager.getContext("FiveM"))
            return;
        if (event.getEnvironment().getSimulator().getType().equals("MANIFEST"))
            return;
        if (!started.contains(event.getEnvironment().getSimulator())) {
            if (!allowedNatives.contains(event.getName())) {
                RefineX.logger.log(LogType.WARNING, "Native '" + event.getName() + "' should not have been called before resource start event.",
                        event.getEnvironment().currentSource());
            }
        }
    }
}
