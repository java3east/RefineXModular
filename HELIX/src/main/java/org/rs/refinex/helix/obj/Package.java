package org.rs.refinex.helix.obj;

import org.rs.refinex.RefineX;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Any;
import org.rs.refinex.value.ExportFunction;

public class Package {
    @ExportFunction
    public static void Require(Environment environment, String path) {
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Package.Require()' might no longer exist", environment.currentSource());
        environment.getResource().getFiles(path).forEach(environment::loadfile);
    }

    @ExportFunction
    public static void Export(Environment environment, String name, Any any) {
        RefineX.logger.log(LogType.WARNING, "OUTDATED API USAGE: 'Package.Export()' might no longer exist", environment.currentSource());
    }
}
