package org.rs.refinex.helix.namespaces;

import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.helix.HelixEventHandler;
import org.rs.refinex.helix.obj.HPlayer;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.Varargs;

/**
 * Native Global functions that should be available to all server side scripts.
 */
public class SERVER extends Namespace {
    /**
     * Registers a server event handler that can be triggered by client scripts.
     *
     * @param environment The scripting environment.
     * @param event      The name of the event to register.
     * @param callback   The function to call when the event is triggered.
     */
    @Native
    public static void RegisterServerEvent(Environment environment, String event, Function callback) {
        environment.addEventHandler(
                new HelixEventHandler(
                        environment,
                        event,
                        callback,
                        true,
                        environment.currentSource()
                )
        );
    }

    /**
     * Triggers a client event for a specific player.
     * @param environment The scripting environment.
     * @param event The name of the event to trigger.
     * @param clientId The ID of the client to trigger the event for.
     * @param varargs The arguments to pass to the event handler.
     */
    @Native
    public static void TriggerClientEvent(Environment environment, String event, long clientId, Varargs varargs) {
        GUID guid = new GUID();
        guid.guid = clientId;
        HPlayer player = (HPlayer) GUID.get(guid, HPlayer.class);
        new ContextEvent(environment.currentSource(), event, environment, player.client(), varargs)
                .dispatch(false);
    }
}
