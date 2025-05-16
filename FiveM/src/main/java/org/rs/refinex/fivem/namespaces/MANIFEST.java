package org.rs.refinex.fivem.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.fivem.Fxmanifest;
import org.rs.refinex.scripting.Environment;

public class MANIFEST extends Namespace {
    @Native
    public static void fx_version(Environment environment, String version) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("fx_version", version);
    }
}
