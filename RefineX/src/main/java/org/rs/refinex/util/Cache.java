package org.rs.refinex.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Cache<T> {
    private final Map<String, T> cache = new HashMap<>();

    public boolean contains(final @NotNull String key) {
        return cache.containsKey(key);
    }

    public @NotNull T get(final @NotNull String key) {
        if (!cache.containsKey(key))
            throw new NoSuchElementException(key);
        return cache.get(key);
    }

    public @NotNull T get(final @NotNull String key, final @NotNull Generator<T> generator) {
        if (cache.containsKey(key))
            return cache.get(key);
        T value = generator.generate();
        cache.put(key, value);
        return value;
    }

    public @NotNull T get(final @NotNull String key, final @NotNull T value) {
        if (cache.containsKey(key))
            return cache.get(key);
        cache.put(key, value);
        return value;
    }

    public void put(final @NotNull String key, final @NotNull T value) {
        cache.put(key, value);
    }
}
