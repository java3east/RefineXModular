package org.rs.refinex.value;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.guid.FunctionReference;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.scripting.Environment;

import java.lang.reflect.*;
import java.util.*;

/**
 * Responsible for mapping objects to a specific type.
 * @param <T> the type to map to
 */
public abstract class ValueMapper<T> {
    private final HashMap<Class<?>, List<Mapping>> mappers = new HashMap<>();
    private final Class<?> clazz;

    private @NotNull Object objMap(final @NotNull Map<String, T> map, final @NotNull Class<?> clazz, Environment environment) {
        if (map.containsKey("__guid__")) {
            long guid = getLongMapper().map(map.get("__guid__"), environment);
            GUID gid = new GUID();
            gid.guid = guid;
            return GUID.get(gid, clazz);
        }
        try {
            Object instance = clazz.getConstructor().newInstance();
            for (String key : map.keySet()) {
                T value = map.get(key);
                try {
                    Field field = clazz.getField(key);
                    ObjectMapper<?> mapper = getMapper(field.getType(), value.getClass(), false, environment);
                    Object mappedValue = mapper.map(value, environment);
                    field.set(instance, mappedValue);
                } catch (NoSuchFieldException ignored){ }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object staticFunctionInterface(final @NotNull Class<?> clazz, Environment environment) {
        try {
            Map<String, T> map = new HashMap<>();
            for (Method method : clazz.getMethods()) {
                if (method.getAnnotation(ExportFunction.class) != null) {
                    boolean isStatic = Modifier.isStatic(method.getModifiers());
                    if (!isStatic) continue;
                    Function fun = Function.of(null, method, environment);
                    String name = method.getName();
                    long ref = environment.functionReference(fun);
                    FunctionReference reference = new FunctionReference();
                    reference.__RFXREF__ = ref;
                    map.put(name, (T) objUnmap(reference, environment));
                }
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private @NotNull Object objUnmap(final @NotNull Object object, Environment environment) {
        if (this.clazz.isAssignableFrom(object.getClass())) {
            return object;
        }
        try {
            Map<String, T> map = new HashMap<>();
            for (Field field : object.getClass().getFields()) {
                Object value = field.get(object);
                ObjectMapper<T> unmapper = (ObjectMapper<T>) getMapper(clazz, value.getClass(), true, environment);
                T mappedValue = unmapper.map(value, environment);
                map.put(field.getName(), mappedValue);
            }
            for (Method method : object.getClass().getMethods()) {
                if (method.getAnnotation(ExportFunction.class) != null) {
                    boolean isStatic = Modifier.isStatic(method.getModifiers());
                    Function fun = Function.of(isStatic ? null : object, method, environment);
                    String name = method.getName();
                    long ref = environment.functionReference(fun);
                    FunctionReference reference = new FunctionReference();
                    reference.__RFXREF__ = ref;
                    map.put(name, (T) objUnmap(reference, environment));
                }
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

    private @NotNull ObjectMapper<?> getMapper(final @NotNull Class<?> to, final @NotNull Class<?> from, boolean unmap, Environment environment) {
        List<Mapping> mappings = mappers.get(to);
        if (mappings == null)
            if (unmap)
                return (ObjectMapper<Object>) (value, env) -> getMapUnmapper().map(ValueMapper.this.objUnmap(value, env), env);
            else
                return (ObjectMapper<Object>) (value, env) -> ValueMapper.this.objMap(ValueMapper.this.getMapMapper().map(value, env), to, env);

        for (Mapping mapping : mappings) {
            if (mapping.clazz().isAssignableFrom(from)) {
                return mapping.mapper();
            }
        }

        if (unmap)
            return (ObjectMapper<Object>) (value, env) -> getMapUnmapper().map(ValueMapper.this.objUnmap(value, env), env);
        else
            return (ObjectMapper<Object>) (value, env) -> ValueMapper.this.objMap(ValueMapper.this.getMapMapper().map(value, env), to, env);
    }

    private @NotNull Object arrayMap(final @NotNull T[] array, final @NotNull Class<?> clazz, Environment environment) {
        try {
            Object instance = Array.newInstance(clazz.getComponentType(), array.length);
            for (int i = 0; i < array.length; i++) {
                Object value = array[i];
                ObjectMapper<?> mapper = getMapper(clazz.getComponentType(), value.getClass(), false, environment);
                Object mappedValue = mapper.map(value, environment);
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
        register(Any.class, clazz, (value, environment) -> new Any(value));

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
        register(clazz, Any.class, (value, environment) -> ((Any) value).o());
    }

    /**
     * Maps the given object to the given class.
     * @param object the object to map
     * @param clazz the class to map to
     * @return the mapped object
     */
    public final @NotNull Object map(final @NotNull T object, final @NotNull Class<?> clazz, Environment environment) {
        if (clazz.isArray()) {
            T[] array = getArrayMapper().map(object, environment);
            return arrayMap((T[]) array, clazz, environment);
        }
        ObjectMapper<?> mapper = getMapper(clazz, object.getClass(), false, environment);
        return mapper.map(object, environment);
    }

    protected abstract Object unmapVarargs(Varargs varargs, Environment environment);

    /**
     * Maps the given object to the class of this ValueMapper.
     * @param object the object to map
     * @return the mapped object
     */
    public final @NotNull T unmap(@NotNull Object object, final @NotNull Environment environment) {
        if (object instanceof Varargs v) object = unmapVarargs(v, environment);
        if (this.clazz.isAssignableFrom(object.getClass())) return (T) object;
        if (object.getClass().isArray()) {
            T[] array = (T[]) Array.newInstance(clazz, Array.getLength(object));
            for (int i = 0; i < array.length; i++) {
                Object value = Array.get(object, i);
                ObjectMapper<?> mapper = getMapper(clazz, value.getClass(), true, environment);
                array[i] = (T) mapper.map(value, environment);
            }
            T t = getArrayUnmapper().map(array, environment);
            t = (T) environment.envTypeFunctionalObject(t, true);
            return t;
        }
        ObjectMapper<T> unmapper = (ObjectMapper<T>) getMapper(clazz, object.getClass(), true, environment);
        T t = unmapper.map(object, environment);
        t = (T) environment.envTypeFunctionalObject(t, true);
        return t;
    }

    public final @NotNull Object[] match(final @NotNull Object[] in, final @NotNull Class<?>[] clazz, Environment environment, boolean ignoreObject) {
        Object[] out = new Object[clazz.length - (ignoreObject ? 1 : 0)];
        for (int i = ignoreObject ? 1 : 0; i < clazz.length; i++) {
            if (i >= in.length) {
                if (clazz[i].isAssignableFrom(Varargs.class))
                    out[i - (ignoreObject ? 1 : 0)] = Varargs.of();
                else
                    out[i - (ignoreObject ? 1 : 0)] = null;
                continue;
            }
            T value = (T) in[i];
            Class<?> targetClass = clazz[i];
            if (Varargs.class.isAssignableFrom(targetClass)) {
                T[] varargs = (T[]) Array.newInstance(this.clazz, in.length - i);
                System.arraycopy(in, i, varargs, 0, in.length - i);
                out[i - (ignoreObject ? 1 : 0)] = new Varargs(varargs);
                i = in.length;
            } else if (isNull(value)) {
                out[i - (ignoreObject ? 1 : 0)] = null;
            }else if (targetClass.isArray()) {
                out[i - (ignoreObject ? 1 : 0)] = arrayMap(getArrayMapper().map(value, environment), targetClass, environment);
            } else {
                ObjectMapper<?> mapper = getMapper(targetClass, value.getClass(), false, environment);
                out[i - (ignoreObject ? 1 : 0)] = mapper.map(value, environment);
            }
        }
        return out;
    }

    public final @NotNull Object[] match(final @NotNull Object[] in, final @NotNull Class<?>[] clazz, Environment environment) {
        return match(in, clazz, environment, true);
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
