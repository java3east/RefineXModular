package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.*;
import org.rs.refinex.RefineX;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.ObjectMapper;
import org.rs.refinex.value.ValueMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * A mapper for LuaValue objects, providing methods to convert between LuaValue and other types.
 * This class extends the ValueMapper class and implements various object mapping methods.
 */
public class LuaValueMapper extends ValueMapper<LuaValue> {
    public LuaValueMapper() {
        super(LuaValue.class);
    }

    private Varargs unmap(org.rs.refinex.value.Varargs varargs, Environment environment) {
        LuaValue[] values = new LuaValue[varargs.length()];
        for (int i = 0; i < varargs.length(); i++) {
            Object o = varargs.values()[i];
            if (o == null) {
                values[i] = LuaValue.NIL;
            } else if (o instanceof LuaValue luaValue) {
                values[i] = luaValue;
            } else {
                values[i] = unmap(varargs.values()[i], environment);
            }
        }
        return LuaValue.varargsOf(values);
    }

    @Override
    protected Object unmapVarargs(org.rs.refinex.value.Varargs varargs, Environment environment) {
        LuaValue[] lValues = new LuaValue[varargs.length()];
        for (int i = 0; i < varargs.length(); i++) {
            Object o = varargs.values()[i];
            lValues[i] = unmap(o, environment);
        }
        return LuaValue.varargsOf(lValues);
    }

    @Override
    public boolean isNull(@NotNull LuaValue object) {
        if (object == null) return true;
        return object.isnil();
    }

    @Override
    public ObjectMapper<Integer> getIntegerMapper() {
        return (value, env) -> ((LuaValue) value).checkint();
    }

    @Override
    public ObjectMapper<Long> getLongMapper() {
        return (value, env) -> ((LuaValue) value).checklong();
    }

    @Override
    public ObjectMapper<Float> getFloatMapper() {
        return (value, env) -> (float) ((LuaValue) value).checkdouble();
    }

    @Override
    public ObjectMapper<Double> getDoubleMapper() {
        return (value, env) -> ((LuaValue) value).checkdouble();
    }

    @Override
    public ObjectMapper<String> getStringMapper() {
        return (value, env) -> ((LuaValue) value).checkjstring();
    }

    @Override
    public ObjectMapper<Boolean> getBooleanMapper() {
        return (value, env) -> ((LuaValue) value).checkboolean();
    }

    @Override
    public ObjectMapper<Map<String, LuaValue>> getMapMapper() {
        return (value, env) -> {
            LuaTable tbl = ((LuaValue) value).checktable();
            Map<String, LuaValue> map = new HashMap<>();
            for (LuaValue key : tbl.keys()) {
                LuaValue val = tbl.get(key);
                LuaString keyStr = key.checkstring();
                map.put(keyStr.checkjstring(), val);
            }
            return map;
        };
    }

    @Override
    public ObjectMapper<LuaValue[]> getArrayMapper() {
        return (value, env) -> {
            LuaTable tbl = ((LuaValue) value).checktable();
            int length = tbl.length();
            LuaValue[] array = new LuaValue[length];
            for (int i = 0; i < length; i++) {
                array[i] = tbl.get(i + 1);
            }
            return array;
        };
    }

    @Override
    public ObjectMapper<Function> getFunctionMapper() {
        return (value, env) -> {
            LuaFunction luaFunc = ((LuaValue) value).checkfunction();
            return (Function) args -> {
                Varargs luaArgs = unmap(args, env);
                Varargs results = luaFunc.invoke(luaArgs);
                return getVarargsMapper().map(results, env);
            };
        };
    }

    @Override
    public ObjectMapper<org.rs.refinex.value.Varargs> getVarargsMapper() {
        return (value, env) -> {
            Varargs varargs = (Varargs) value;
            LuaValue[] values = new LuaValue[varargs.narg()];
            for (int i = 0; i < varargs.narg(); i++) {
                values[i] = varargs.arg(i + 1);
            }
            return new org.rs.refinex.value.Varargs(values);
        };
    }

    @Override
    public ObjectMapper<LuaValue> getIntegerUnmapper() {
        return (value, env) -> LuaValue.valueOf((Integer) value);
    }

    @Override
    public ObjectMapper<LuaValue> getLongUnmapper() {
        return (value, env) -> LuaValue.valueOf((Long) value);
    }

    @Override
    public ObjectMapper<LuaValue> getFloatUnmapper() {
        return (value, env) -> LuaValue.valueOf((Float) value);
    }

    @Override
    public ObjectMapper<LuaValue> getDoubleUnmapper() {
        return (value, env) -> LuaValue.valueOf((Double) value);
    }

    @Override
    public ObjectMapper<LuaValue> getStringUnmapper() {
        return (value, env) -> LuaValue.valueOf((String) value);
    }

    @Override
    public ObjectMapper<LuaValue> getBooleanUnmapper() {
        return (value, env) -> LuaValue.valueOf((Boolean) value);
    }

    @Override
    public ObjectMapper<LuaValue> getMapUnmapper() {
        return (value, env) -> {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) value;
            LuaTable tbl = new LuaTable();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                tbl.set(LuaValue.valueOf(entry.getKey()), this.unmap(entry.getValue(), env));
            }
            return tbl;
        };
    }

    @Override
    public ObjectMapper<LuaValue> getArrayUnmapper() {
        return (value, env) -> {
            LuaTable tbl = new LuaTable();
            LuaValue[] array = (LuaValue[]) value;
            for (int i = 0; i < array.length; i++) {
                tbl.set(i + 1, array[i]);
            }
            return tbl;
        };
    }

    @Override
    public ObjectMapper<LuaValue> getFunctionUnmapper() {
        return (value, env) -> {
            if (!(value instanceof Function func)) {
                RefineX.logger.log(LogType.ERROR, "value is not a function", LogSource.here());
                return LuaValue.NIL;
            }
            return new LuaFunction() {
                @Override
                public Varargs invoke(Varargs args) {
                    org.rs.refinex.value.Varargs results = func.invoke(getVarargsMapper().map(args, env));
                    return unmap(results, env);
                }
            };
        };
    }
}
