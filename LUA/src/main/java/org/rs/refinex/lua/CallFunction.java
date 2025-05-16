package org.rs.refinex.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.rs.refinex.scripting.Environment;

import java.lang.reflect.Method;

class CallFunction extends VarArgFunction {
    private final Environment environment;

    CallFunction(Environment environment) {
        this.environment = environment;
    }

    private Object invoke(Method m, Object[] args) {
        try {
            return m.invoke(null, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke function " + m.getName(), e);
        }
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        if (varargs.narg() < 1) {
            throw new IllegalArgumentException("Function expects at least one argument");
        }
        String funName = varargs.checkjstring(1);
        Method m = environment.getFunction(funName);
        Object[] args = new Object[m.getParameterCount()];
        LuaValueMapper mapper = new LuaValueMapper();
        args[0] = environment;
        for (int i = 1; i < m.getParameterCount(); i++)
            args[i] = mapper.map(varargs.arg(i + 1), m.getParameterTypes()[i]);

        Object result = invoke(m, args);
        if (result == null) return LuaValue.NIL;
        return mapper.unmap(result);
    }
}
