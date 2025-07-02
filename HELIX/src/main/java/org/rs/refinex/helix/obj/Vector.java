package org.rs.refinex.helix.obj;

import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Referencable;

public class Vector extends Referencable {
    public double x;
    public double y;
    public double z;

    @ExportFunction(isMetaFunction = true)
    public static Vector __call(Environment environment, Vector vec, double x, double y, double z) {
        Vector vector = new Vector();
        vector.x = x;
        vector.y = y;
        vector.z = z;
        return vector;
    }

    @ExportFunction(isMetaFunction = true)
    public String __tostring(Environment environment) {
        return String.format("Vector(%f, %f, %f)", this.x, this.y, this.z);
    }

    @ExportFunction(isMetaFunction = true)
    public static boolean __equ(Environment environment, Vector a, Vector other) {
        return a.x == other.x && a.y == other.y && a.z == other.z;
    }
}
