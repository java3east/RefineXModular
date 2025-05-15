package org.rs.refinex.value;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ValueMapper<T> {
    private final HashMap<Class<?>, List<Mapping>> mappers = new HashMap<>();
    private final Class<?> clazz;

    private @NotNull Object objMap(final @NotNull Map<String, T> map, final @NotNull Class<?> clazz) {
        try {
            Object instance = clazz.getConstructor().newInstance();
            for (String key : map.keySet()) {
                T value = map.get(key);
                Field field = clazz.getField(key);
                ObjectMapper<?> mapper = getMapper(field.getType(), value.getClass(), false);
                Object mappedValue = mapper.map(value);
                field.set(instance, mappedValue);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private @NotNull Object objUnmap(final @NotNull Object object) {
        try {
            Map<String, T> map = new HashMap<>();
            for (Field field : object.getClass().getFields()) {
                Object value = field.get(object);
                ObjectMapper<T> unmapper = (ObjectMapper<T>) getMapper(clazz, value.getClass(), true);
                T mappedValue = unmapper.map(value);
                map.put(field.getName(), mappedValue);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void register(Class<?> to, Class<?> from, ObjectMapper<?> mapper) {
        if (!mappers.containsKey(to)) {
            mappers.put(to, new ArrayList<>());
        }
        mappers.get(to).add(new Mapping(from, mapper));
    }

    private @NotNull ObjectMapper<?> getMapper(final @NotNull Class<?> to, final @NotNull Class<?> from, boolean unmap) {
        List<Mapping> mappings = mappers.get(to);
        if (mappings == null)
            if (unmap)
                return (ObjectMapper<Object>) value -> getMapUnmapper().map(ValueMapper.this.objUnmap(value));
            else
                return (ObjectMapper<Object>) value -> ValueMapper.this.objMap(ValueMapper.this.getMapMapper().map(value), to);

        for (Mapping mapping : mappings) {
            if (mapping.clazz().isAssignableFrom(from)) {
                return mapping.mapper();
            }
        }

        if (unmap)
            return (ObjectMapper<Object>) value -> getMapUnmapper().map(ValueMapper.this.objUnmap(value));
        else
            return (ObjectMapper<Object>) value -> ValueMapper.this.objMap(ValueMapper.this.getMapMapper().map(value), to);
    }

    public ValueMapper(Class<? extends T> clazz) {
        this.clazz = clazz;
        register(Integer.class, clazz, getIntegerMapper());
        register(int.class, clazz, getIntegerMapper());
        register(Long.class, clazz, getLongMapper());
        register(long.class, clazz, getLongMapper());
        register(Float.class, clazz, getFloatMapper());
        register(float.class, clazz, getFloatMapper());
        register(Double.class, clazz, getDoubleMapper());
        register(double.class, clazz, getDoubleMapper());
        register(String.class, clazz, getStringMapper());
        register(Boolean.class, clazz, getBooleanMapper());
        register(boolean.class, clazz, getBooleanMapper());
        register(Map.class, clazz, getMapMapper());
        register(clazz, Integer.class, getIntegerUnmapper());
        register(clazz, int.class, getIntegerUnmapper());
        register(clazz, Long.class, getLongUnmapper());
        register(clazz, long.class, getLongUnmapper());
        register(clazz, Float.class, getFloatUnmapper());
        register(clazz, float.class, getFloatUnmapper());
        register(clazz, Double.class, getDoubleUnmapper());
        register(clazz, double.class, getDoubleUnmapper());
        register(clazz, String.class, getStringUnmapper());
        register(clazz, Boolean.class, getBooleanUnmapper());
        register(clazz, boolean.class, getBooleanUnmapper());
        register(clazz, Map.class, getMapUnmapper());
    }

    public final @NotNull Object map(final @NotNull T object, final @NotNull Class<?> clazz) {
        ObjectMapper<?> mapper = getMapper(clazz, object.getClass(), false);
        return mapper.map(object);
    }

    public final @NotNull T unmap(final @NotNull Object object) {
        ObjectMapper<T> unmapper = (ObjectMapper<T>) getMapper(clazz, object.getClass(), true);
        return unmapper.map(object);
    }

    public abstract ObjectMapper<Integer> getIntegerMapper();
    public abstract ObjectMapper<Long> getLongMapper();
    public abstract ObjectMapper<Float> getFloatMapper();
    public abstract ObjectMapper<Double> getDoubleMapper();
    public abstract ObjectMapper<String> getStringMapper();
    public abstract ObjectMapper<Boolean> getBooleanMapper();
    public abstract ObjectMapper<Map<String, T>> getMapMapper();

    public abstract ObjectMapper<T> getIntegerUnmapper();
    public abstract ObjectMapper<T> getLongUnmapper();
    public abstract ObjectMapper<T> getFloatUnmapper();
    public abstract ObjectMapper<T> getDoubleUnmapper();
    public abstract ObjectMapper<T> getStringUnmapper();
    public abstract ObjectMapper<T> getBooleanUnmapper();
    public abstract ObjectMapper<T> getMapUnmapper();
}
