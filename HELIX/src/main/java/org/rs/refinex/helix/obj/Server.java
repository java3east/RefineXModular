package org.rs.refinex.helix.obj;

import org.rs.refinex.RefineX;
import org.rs.refinex.helix.HelixEventHandler;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Function;

public class Server {
    @ExportFunction
    public static void Subscribe(Environment environment, String event, Function callback) {
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Server.Subscribe()' might no longer exist", environment.currentSource());
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
