package org.rs.refinex.guid;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class GUID {
    public long guid;

    public GUID() {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GUID guid1 = (GUID) o;
        return guid == guid1.guid;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(guid);
    }

    private static long nextGUID = 1;
    private static final HashMap<GUID, Object> guidMap = new HashMap<>();

    public static GUID register(Object o) {
        GUID guid = new GUID();
        guid.guid = nextGUID++;
        guidMap.put(guid, o);
        return guid;
    }

    public static @NotNull Object get(GUID guid, Class<?> clazz) {
        Object o = guidMap.get(guid);
        if (!clazz.isInstance(o)) {
            throw new ClassCastException("Object is not of type " + clazz.getName());
        }
        return o;
    }
}
