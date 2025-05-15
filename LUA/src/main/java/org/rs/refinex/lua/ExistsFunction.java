package org.rs.refinex.lua;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.lang.reflect.Method;

class ExistsFunction extends VarArgFunction {
    private final LuaEnvironment environment;

    ExistsFunction(LuaEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        if (varargs.narg() < 1) {
            throw new LuaError("Function expects at least one argument");
        }
        String funName = varargs.checkjstring(1);
        return LuaValue.valueOf(environment.hasFunction(funName));
    }
}
