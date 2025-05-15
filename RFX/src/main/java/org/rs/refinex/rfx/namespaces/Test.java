package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.scripting.Environment;

public class Test extends Namespace {
    public int x;

    @Native
    public static Object[] test(Environment environment, String name, int i, double d, boolean b, Test test) {
        System.out.println(environment);
        System.out.println(name);
        System.out.println(i);
        System.out.println(d);
        System.out.println(b);
        System.out.println(test + " (" + test.x + ")");
        return new Object[]{ 1, 1.0, "test", true, new Test() {{this.x = 2;}} };
    }
}
