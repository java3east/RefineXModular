package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.LimitList;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Represents the manifest of a resource.
 * A manifest contains metadata about the resource (e.g. name, version, author, server / client scripts, ...)
 */
public abstract class Manifest extends Simulator {
    protected final HashMap<String, List<Object>> data = new HashMap<>();

    /**
     * Creates a new instance of the manifest.
     * @param simulation the simulation this manifest belongs to
     */
    public Manifest(@NotNull Simulation simulation) {
        super(simulation, "MANIFEST");
    }

    public void set(@NotNull String list, @NotNull String value) {
        List<Object> values = data.getOrDefault(list, new LimitList<>(-1));
        values.add(value);
        data.put(list, values);
    }

    public Object[] get(@NotNull String key) {
        if (!data.containsKey(key) || data.get(key).isEmpty()) {
            Optional<Object> value = getData(key);
            if (value.isPresent()) {
                return new Object[]{value.get()};
            }
        }
        return data.getOrDefault(key, new LimitList<>(-1)).toArray(new Object[0]);
    }
    /**
     * Adds all the values to the given key.
     * @param key the key to set the values for
     * @param values the values to set
     */
    public void set(final @NotNull String key, final @NotNull String[] values) {
        for (String value : values) {
            set(key, value);
        }
    }

    /**
     * Validates the given values
     *
     * @throws IllegalStateException if the values are not valid
     *
     * @param key the key of the values to validate
     * @param min the minimum amount of values
     * @param max the maximum amount of values
     * @param allowed the allowed values, if empty all values are allowed
     */
    protected void validate(@NotNull String key, int min, int max, List<Object> allowed) {
        Object[] values = get(key);
        if (values.length < min) {
            throw new IllegalStateException("Not enough values for " + key + ", expected at least " + min);
        }
        if (values.length > max && max != -1) {
            throw new IllegalStateException("Too many values for " + key + ", expected at most " + max);
        }
        if (!allowed.isEmpty()) {
            for (Object value : values) {
                if (!allowed.contains(value)) {
                    throw new IllegalStateException("Invalid value for " + key + ": " + value);
                }
            }
        }
    }

    /**
     * Makes sure the manifest is valid.
     * This should check if all required values are set and if the values are valid.
     * @throws IllegalStateException if the manifest is not valid
     */
    public abstract void validate();
}
