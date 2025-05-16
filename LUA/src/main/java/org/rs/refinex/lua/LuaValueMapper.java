package org.rs.refinex.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.rs.refinex.value.ObjectMapper;
import org.rs.refinex.value.ValueMapper;

import java.util.HashMap;
import java.util.Map;

public class LuaValueMapper extends ValueMapper<LuaValue> {
    public LuaValueMapper() {
        super(LuaValue.class);
    }

    @Override
    public ObjectMapper<Integer> getIntegerMapper() {
        return value -> ((LuaValue) value).checkint();
    }

    @Override
    public ObjectMapper<Long> getLongMapper() {
        return value -> ((LuaValue) value).checklong();
    }

    @Override
    public ObjectMapper<Float> getFloatMapper() {
        return value -> (float) ((LuaValue) value).checkdouble();
    }

    @Override
    public ObjectMapper<Double> getDoubleMapper() {
        return value -> ((LuaValue) value).checkdouble();
    }

    @Override
    public ObjectMapper<String> getStringMapper() {
        return value -> ((LuaValue) value).checkjstring();
    }

    @Override
    public ObjectMapper<Boolean> getBooleanMapper() {
        return value -> ((LuaValue) value).checkboolean();
    }

    @Override
    public ObjectMapper<Map<String, LuaValue>> getMapMapper() {
        return (value) -> {
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
        return value -> {
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
    public ObjectMapper<LuaValue> getIntegerUnmapper() {
        return value -> LuaValue.valueOf((Integer) value);
    }

    @Override
    public ObjectMapper<LuaValue> getLongUnmapper() {
        return value -> LuaValue.valueOf((Long) value);
    }

    @Override
    public ObjectMapper<LuaValue> getFloatUnmapper() {
        return value -> LuaValue.valueOf((Float) value);
    }

    @Override
    public ObjectMapper<LuaValue> getDoubleUnmapper() {
        return value -> LuaValue.valueOf((Double) value);
    }

    @Override
    public ObjectMapper<LuaValue> getStringUnmapper() {
        return value -> LuaValue.valueOf((String) value);
    }

    @Override
    public ObjectMapper<LuaValue> getBooleanUnmapper() {
        return value -> LuaValue.valueOf((Boolean) value);
    }

    @Override
    public ObjectMapper<LuaValue> getMapUnmapper() {
        return value -> {
            Map<String, LuaValue> map = (Map<String, LuaValue>) value;
            LuaTable tbl = new LuaTable();
            for (Map.Entry<String, LuaValue> entry : map.entrySet()) {
                tbl.set(LuaValue.valueOf(entry.getKey()), entry.getValue());
            }
            return tbl;
        };
    }

    @Override
    public ObjectMapper<LuaValue> getArrayUnmapper() {
        return value -> {
            LuaTable tbl = new LuaTable();
            LuaValue[] array = (LuaValue[]) value;
            for (int i = 0; i < array.length; i++) {
                tbl.set(i + 1, array[i]);
            }
            return tbl;
        };
    }
}
