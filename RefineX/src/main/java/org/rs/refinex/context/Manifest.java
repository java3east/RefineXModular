package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the manifest of a resource.
 * A manifest contains metadata about the resource (e.g. name, version, author, server / client scripts, ...)
 */
public abstract class Manifest extends Simulator {
    /**
     * Creates a new instance of the manifest.
     * @param simulation the simulation this manifest belongs to
     */
    public Manifest(@NotNull Simulation simulation) {
        super(simulation);
    }

    /**
     * Sets the value for the given key.
     * @param key the key to set the value for
     * @param value the value to set
     */
    public abstract void set(final @NotNull String key, final @NotNull String value);

    /**
     * Returns the values saved for the given key.
     * If the key does not exist, an empty array is returned.
     * @param key the key to get the values for
     * @return the values saved for the given key
     */
    public abstract String[] get(final @NotNull String key);

    /**
     * Validates the given values
     *
     * @throws IllegalStateException if the values are not valid
     *
     * @param values the values to validate
     * @param min the minimum amount of values
     * @param max the maximum amount of values
     * @param allowed the allowed values, if empty all values are allowed
     */
    protected void validate(@NotNull String[] values, int min, int max, List<String> allowed) {
        if (values.length < min) {
            throw new IllegalStateException("Not enough values for " + Arrays.toString(values) + ", expected at least " + min);
        }
        if (values.length > max) {
            throw new IllegalStateException("Too many values for " + Arrays.toString(values) + ", expected at most " + max);
        }
        if (!allowed.isEmpty()) {
            for (String value : values) {
                if (!allowed.contains(value)) {
                    throw new IllegalStateException("Invalid value for " + Arrays.toString(values) + ": " + value);
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
