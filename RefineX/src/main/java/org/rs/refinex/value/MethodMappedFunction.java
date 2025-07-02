package org.rs.refinex.value;

import org.rs.refinex.scripting.Environment;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodMappedFunction implements Function {
    private final Object o;
    private final Method method;
    private final Environment environment;

    public MethodMappedFunction(Object o, Method method, Environment environment) {
        this.o = o;
        this.method = method;
        this.environment = environment;
    }

    private Object invoke(Object[] args) {
        try {
            return method.invoke(o, args);
        } catch (Exception e) {
            String givenAndExpected = "Given: " + Arrays.toString(Arrays.stream(args).map(Object::getClass).map(Object::toString).toArray());
            givenAndExpected += ", Expected: " + Arrays.toString(method.getParameterTypes());
            throw new RuntimeException("Failed to invoke function '" + method.getName() + "': " + e.getMessage() + " " + givenAndExpected, e);
        }
    }

    private ValueMapper<?> getMapper() {
        return this.environment.getLanguage().getValueMapper();
    }

    @Override
    public Varargs invoke(Varargs vargs) {
        Object[] luaArgs = new Object[method.getParameterCount()];
        Object[] vals = vargs.values();
        for (int i = 0; i < luaArgs.length - 1; i++) {
            if (vals.length <= i) break;
            luaArgs[i + 1] = vals[i];
        }

        Class<?>[] params = method.getParameterTypes();
        ValueMapper<?> mapper = getMapper();
        Object[] mappedArgs = mapper.match(luaArgs, params, environment, false);
        mappedArgs[0] = environment;
        Object result = invoke(mappedArgs);
        if (result == null) return Varargs.of();
        return Varargs.of(mapper.unmap(result, environment));
    }
}
