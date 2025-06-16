package org.rs.refinex.value;

import org.rs.refinex.scripting.Environment;

import java.lang.reflect.Method;

public interface Function {
    static Function of(Object o, Method m, Environment e) {
        return new MethodMappedFunction(o, m, e);
    }
    Varargs invoke(Varargs args);
}
