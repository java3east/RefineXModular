package org.rs.refinex.value;

import java.util.Arrays;

public record Varargs(Object[] values) {
    public static Varargs empty() {
        return of();
    }

    public static Varargs of(Object... values) {
        return new Varargs(values);
    }

    public int length() {
        return values.length;
    }

    @Override
    public String toString() {
        return "Varargs(" +
                 Arrays.toString(values) +
                ')';
    }
}
