package org.rs.refinex.toml;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.ObjectMapper;
import org.rs.refinex.value.ValueMapper;
import org.rs.refinex.value.Varargs;

import java.util.Map;

public class TomlValueMapper extends ValueMapper<Object> {
    public TomlValueMapper() {
        super(Object.class);
    }

    @Override
    public boolean isNull(@NotNull Object object) {
        return false;
    }

    @Override
    public ObjectMapper<Integer> getIntegerMapper() {
        return value -> (Integer)  value;
    }

    @Override
    public ObjectMapper<Long> getLongMapper() {
        return value -> (Long) value;
    }

    @Override
    public ObjectMapper<Float> getFloatMapper() {
        return value -> (Float) value;
    }

    @Override
    public ObjectMapper<Double> getDoubleMapper() {
        return value -> (Double) value;
    }

    @Override
    public ObjectMapper<String> getStringMapper() {
        return value -> (String) value;
    }

    @Override
    public ObjectMapper<Boolean> getBooleanMapper() {
        return value -> (Boolean) value;
    }

    @Override
    public ObjectMapper<Map<String, Object>> getMapMapper() {
        return value -> (Map<String, Object>) value;
    }

    @Override
    public ObjectMapper<Object[]> getArrayMapper() {
        return value -> (Object[]) value;
    }

    @Override
    public ObjectMapper<Function> getFunctionMapper() {
        return value -> (Function) value;
    }

    @Override
    public ObjectMapper<Varargs> getVarargsMapper() {
        return value -> (Varargs) value;
    }

    @Override
    public ObjectMapper<Object> getIntegerUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getLongUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getFloatUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getDoubleUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getStringUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getBooleanUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getMapUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getArrayUnmapper() {
        return value -> value;
    }

    @Override
    public ObjectMapper<Object> getFunctionUnmapper() {
        return value -> value;
    }
}
