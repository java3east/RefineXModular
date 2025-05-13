package org.rs.refinex.fivem;

import org.rs.refinex.plugin.ContextPlugin;

public class Main extends ContextPlugin {
    public Main() {
        super("FiveM");
    }

    @Override
    public void onLoad() {
        System.out.println("FiveM plugin loaded");
    }
}
