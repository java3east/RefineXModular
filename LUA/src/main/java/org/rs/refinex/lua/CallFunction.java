package org.rs.refinex.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.rs.refinex.RefineX;
import org.rs.refinex.event.NativeCallEvent;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * CallFunction is a Lua function that allows calling Java methods from Lua.
 */
class CallFunction extends VarArgFunction {
    private final Environment environment;

    /**
     * Creates a new CallFunction instance with the given environment.
     * The environment will be passed as the first argument to all the java methods.
     * @param environment the environment to use
     */
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
        LuaValue[] luaArgs = new LuaValue[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            luaArgs[i - 1] = varargs.arg(i + 1);
        }
        Object[] mappedArgs = mapper.match(luaArgs, m.getParameterTypes());
        args[0] = environment;
        System.arraycopy(mappedArgs, 0, args, 1, mappedArgs.length);
        RefineX.manager.dispatchEvent(new NativeCallEvent(environment, funName, args));
        Object result = invoke(m, args);
        if (result == null) return LuaValue.NIL;
        return mapper.unmap(result);
    }
}
