package org.rs.refinex.helix.obj;

import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.ExportFunction;

public class Package {
    @ExportFunction
    public static void Require(Environment environment, String path) {
        environment.getResource().getFiles(path).forEach(environment::loadfile);
    }
}
