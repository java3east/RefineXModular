package org.rs.refinex.value;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.scripting.Environment;

/**
 * Maps an object to a specific type.
 * @param <A> the type to map to
 */
public interface ObjectMapper<A> {
    /**
     * Maps the given object to the specified type.
     * @param value the object to be mapped
     * @return the mapped object
     */
    A map(@NotNull Object value, Environment environment);
}
