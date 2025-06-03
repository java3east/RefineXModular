package org.rs.refinex.value;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Responsible for mapping objects to a specific type.
 * @param <T> the type to map to
 */
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

    private @NotNull Object arrayMap(final @NotNull T[] array, final @NotNull Class<?> clazz) {
        try {
            Object instance = Array.newInstance(clazz.getComponentType(), array.length);
            for (int i = 0; i < array.length; i++) {
                Object value = array[i];
                ObjectMapper<?> mapper = getMapper(clazz.getComponentType(), value.getClass(), false);
                Object mappedValue = mapper.map(value);
                Array.set(instance, i, mappedValue);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error while instantiating " + clazz.getName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Creates a new ValueMapper instance.
     * @param clazz the class to map to
     */
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
        register(Object[].class, clazz, getArrayMapper());
        register(Function.class, clazz, getFunctionMapper());

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
        register(clazz, Object[].class, getArrayUnmapper());
        register(clazz, Function.class, getFunctionUnmapper());
    }

    /**
     * Maps the given object to the given class.
     * @param object the object to map
     * @param clazz the class to map to
     * @return the mapped object
     */
    public final @NotNull Object map(final @NotNull T object, final @NotNull Class<?> clazz) {
        if (clazz.isArray()) {
            T[] array = getArrayMapper().map(object);
            return arrayMap((T[]) array, clazz);
        }
        ObjectMapper<?> mapper = getMapper(clazz, object.getClass(), false);
        return mapper.map(object);
    }

    /**
     * Maps the given object to the class of this ValueMapper.
     * @param object the object to map
     * @return the mapped object
     */
    public final @NotNull T unmap(final @NotNull Object object) {
        if (object.getClass().isArray()) {
            T[] array = (T[]) Array.newInstance(clazz, Array.getLength(object));
            for (int i = 0; i < array.length; i++) {
                Object value = Array.get(object, i);
                ObjectMapper<?> mapper = getMapper(clazz, value.getClass(), true);
                array[i] = (T) mapper.map(value);
            }
            return getArrayUnmapper().map(array);
        }
        ObjectMapper<T> unmapper = (ObjectMapper<T>) getMapper(clazz, object.getClass(), true);
        return unmapper.map(object);
    }

    public final @NotNull Object[] match(final @NotNull T[] in, final @NotNull Class<?>[] clazz) {
        Object[] out = new Object[in.length];
        for (int i = 0; i < in.length; i++) {
            T value = in[i];
            Class<?> targetClass = clazz[i + 1];
            if (Varargs.class.isAssignableFrom(targetClass)) {
                T[] varargs = (T[]) Array.newInstance(this.clazz, in.length - i);
                System.arraycopy(in, i, varargs, 0, in.length - i);
                out[i] = new Varargs(varargs);
                i = in.length;
            } else if (isNull(value)) {
                out[i] = null;
                continue;
            }else if (targetClass.isArray()) {
                out[i] = arrayMap(getArrayMapper().map(value), targetClass);
            } else {
                ObjectMapper<?> mapper = getMapper(targetClass, value.getClass(), false);
                out[i] = mapper.map(value);
            }
        }
        return out;
    }

    public abstract boolean isNull(final @NotNull T object);

    public abstract ObjectMapper<Integer> getIntegerMapper();
    public abstract ObjectMapper<Long> getLongMapper();
    public abstract ObjectMapper<Float> getFloatMapper();
    public abstract ObjectMapper<Double> getDoubleMapper();
    public abstract ObjectMapper<String> getStringMapper();
    public abstract ObjectMapper<Boolean> getBooleanMapper();
    public abstract ObjectMapper<Map<String, T>> getMapMapper();
    public abstract ObjectMapper<T[]> getArrayMapper();
    public abstract ObjectMapper<Function> getFunctionMapper();
    public abstract ObjectMapper<Varargs> getVarargsMapper();

    public abstract ObjectMapper<T> getIntegerUnmapper();
    public abstract ObjectMapper<T> getLongUnmapper();
    public abstract ObjectMapper<T> getFloatUnmapper();
    public abstract ObjectMapper<T> getDoubleUnmapper();
    public abstract ObjectMapper<T> getStringUnmapper();
    public abstract ObjectMapper<T> getBooleanUnmapper();
    public abstract ObjectMapper<T> getMapUnmapper();
    public abstract ObjectMapper<T> getArrayUnmapper();
    public abstract ObjectMapper<T> getFunctionUnmapper();
}
