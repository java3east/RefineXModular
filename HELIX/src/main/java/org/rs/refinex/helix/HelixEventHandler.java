package org.rs.refinex.helix;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Function;

public class HelixEventHandler extends ContextEventHandler {
    private final boolean isNet;
    private final String receiverType;

    public HelixEventHandler(@NotNull Environment environment, @NotNull String name, @NotNull Function handler, boolean isNet, @NotNull LogSource origin, String receiverType) {
        super(environment, name, handler, origin);
        this.isNet = isNet;
        this.receiverType = receiverType;
    }

    public HelixEventHandler(@NotNull Environment environment, @NotNull String name, @NotNull Function handler, boolean isNet, @NotNull LogSource origin) {
        this(environment, name, handler, isNet, origin, "DEFAULT");
    }


    @Override
    public void handle(@NotNull ContextEvent event) {
        String eventType = event.get("type") != null ? event.get("type").toString() : "DEFAULT";
        if (!eventType.equals(this.receiverType)) {
            return;
        }
        boolean eventIsNet = event.source() != null && event.source().getSimulator() != event.target();
        if (eventIsNet && !isNet) {
            RefineX.logger.log(LogType.WARNING, "Event handler is not a network event handler, but received a network event", this.source());
            return;
        } else if (!eventIsNet && isNet) {
            RefineX.logger.log(LogType.WARNING, "Event handler is a network event handler, but received a non-network event", this.source());
            return;
        }

        if (event.source() != null && event.source().getSimulator().getData("server_id").isPresent())
            environment.set("source", event.source().getSimulator().getData("server_id").get(), true);

        function.invoke(event.data());
    }
}
