package org.rs.refinex.value;

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
}
