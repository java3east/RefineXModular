package org.rs.refinex.fivem.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.fivem.Fxmanifest;
import org.rs.refinex.scripting.Environment;

/**
 * Namespace for the FiveM Fxmanifest.
 */
public class MANIFEST extends Namespace {
    @Native
    public static void fx_version(Environment environment, String version) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("fx_version", version);
    }

    @Native
    public static void name(Environment environment, String name) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("name", name);
    }
}
