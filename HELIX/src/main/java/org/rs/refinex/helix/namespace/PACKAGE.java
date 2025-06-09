package org.rs.refinex.helix.namespace;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.scripting.Environment;

public class PACKAGE extends Namespace {
    @Native
    public static void PACKAGE_REQUIRE(Environment environment, String path) {
        environment.getResource().getFiles("" + path).forEach(environment::loadfile);
    }
}
