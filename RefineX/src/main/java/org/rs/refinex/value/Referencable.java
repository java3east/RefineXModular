package org.rs.refinex.value;

import org.rs.refinex.guid.GUID;

public abstract class Referencable {
    public long __guid__ = GUID.register(this).guid;
}
