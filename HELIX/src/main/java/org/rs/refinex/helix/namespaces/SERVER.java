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

public class SERVER extends Namespace {
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

    @Native
    public static void TriggerClientEvent(Environment environment, String event, long clientId, Varargs varargs) {
        GUID guid = new GUID();
        guid.guid = clientId;
        HPlayer player = (HPlayer) GUID.get(guid, HPlayer.class);
        new ContextEvent(environment.currentSource(), event, environment, player.client(), varargs)
                .dispatch(false);
    }
}
