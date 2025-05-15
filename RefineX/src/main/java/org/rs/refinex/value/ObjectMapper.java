package org.rs.refinex.value;

import org.jetbrains.annotations.NotNull;

public interface ObjectMapper<A> {
    A map(@NotNull Object value);
}
