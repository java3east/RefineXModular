package org.rs.refinex.fivem;

import org.rs.refinex.plugin.Context;

public class Main extends Context {
    public Main() {
        super("FiveM");
    }

    @Override
    public void onLoad() {
        System.out.println("FiveM plugin loaded");
    }
}
