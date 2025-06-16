package org.rs.refinex.helix.obj;

import org.rs.refinex.helix.HelixEventHandler;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Function;

public class Server {
    @ExportFunction
    public static void Subscribe(Environment environment, String event, Function callback) {
        environment.addEventHandler(
                new HelixEventHandler(
                        environment,
                        event,
                        callback,
                        false,
                        environment.currentSource(),
                        "SERVER"
                )
        );
    }
}
