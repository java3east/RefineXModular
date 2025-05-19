package org.rs.refinex.context;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.simulation.Simulation;
import org.rs.refinex.simulation.Simulator;

public abstract class Manifest extends Simulator {
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
     * Makes sure the manifest is valid.
     * This should check if all required values are set and if the values are valid.
     * @throws IllegalStateException if the manifest is not valid
     */
    public abstract void validate();
}
