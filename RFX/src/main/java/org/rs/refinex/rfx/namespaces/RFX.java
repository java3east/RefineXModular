package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.RefineX;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;

public class RFX extends Namespace {
    @Native
    public static void RFX_REQUIRE(Environment environment, String path) {
        environment.loadfile(path);
    }

    @Native
    public static void RFX_ERROR(Environment environment, String message) {
        RefineX.logger.log(LogType.ERROR, message, environment.currentSource());
    }
}
