package org.rs.refinex.toml;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Environment;
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
    protected Object unmapVarargs(Varargs varargs, Environment environment) {
        return null;
    }

    @Override
    public boolean isNull(@NotNull Object object) {
        return false;
    }

    @Override
    public ObjectMapper<Integer> getIntegerMapper() {
        return (value, env) -> (Integer)  value;
    }

    @Override
    public ObjectMapper<Long> getLongMapper() {
        return (value, env) -> (Long) value;
    }

    @Override
    public ObjectMapper<Float> getFloatMapper() {
        return (value, env) -> (Float) value;
    }

    @Override
    public ObjectMapper<Double> getDoubleMapper() {
        return (value, env) -> (Double) value;
    }

    @Override
    public ObjectMapper<String> getStringMapper() {
        return (value, env) -> (String) value;
    }

    @Override
    public ObjectMapper<Boolean> getBooleanMapper() {
        return (value, env) -> (Boolean) value;
    }

    @Override
    public ObjectMapper<Map<String, Object>> getMapMapper() {
        return (value, env) -> (Map<String, Object>) value;
    }

    @Override
    public ObjectMapper<Object[]> getArrayMapper() {
        return (value, env) -> (Object[]) value;
    }

    @Override
    public ObjectMapper<Function> getFunctionMapper() {
        return (value, env) -> (Function) value;
    }

    @Override
    public ObjectMapper<Varargs> getVarargsMapper() {
        return (value, env) -> (Varargs) value;
    }

    @Override
    public ObjectMapper<Object> getIntegerUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getLongUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getFloatUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getDoubleUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getStringUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getBooleanUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getMapUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getArrayUnmapper() {
        return (value, env) -> value;
    }

    @Override
    public ObjectMapper<Object> getFunctionUnmapper() {
        return (value, env) -> value;
    }
}
