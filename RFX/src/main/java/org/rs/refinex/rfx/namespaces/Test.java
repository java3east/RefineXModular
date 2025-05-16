package org.rs.refinex.rfx.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.scripting.Environment;

import java.util.Arrays;

public class Test extends Namespace {
    public int x;

    @Override
    public String toString() {
        return "Test{" +
                "x=" + x +
                '}';
    }

    @Native
    public static Object[] test(Environment environment, String name, int i, double d, boolean b, Test[] test) {
        System.out.println(environment);
        System.out.println(name);
        System.out.println(i);
        System.out.println(d);
        System.out.println(b);
        System.out.println(Arrays.toString(test));
        return new Object[]{ environment, name, i, d, b, test, new Object[]{"test", 1} };
    }
}
