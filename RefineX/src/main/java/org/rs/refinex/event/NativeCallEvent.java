package org.rs.refinex.event;

import org.rs.refinex.scripting.Environment;

import java.util.Arrays;

public class NativeCallEvent extends Event {
    private final Environment environment;
    private final String name;
    private final Object[] args;

    public NativeCallEvent(Environment environment, String name, Object ...args) {
        super("native_call");
        this.environment = environment;
        this.name = name;
        this.args = args;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getName() {
        return name;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "NativeCallEvent{" +
                "args=" + Arrays.toString(args) +
                ", environment=" + environment +
                ", name='" + name + '\'' +
                '}';
    }
}
