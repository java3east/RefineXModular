package org.rs.refinex.helix.obj;

import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Referencable;

public class Controller extends Referencable {
    protected String name;

    /**
     * Sets the name of the controller.
     * @param name the name to set
     */
    @ExportFunction
    public void SetName(String name) {
        this.name = name;
    }
}
