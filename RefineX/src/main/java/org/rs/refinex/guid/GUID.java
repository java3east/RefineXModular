package org.rs.refinex.guid;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * A GUID (Globally Unique Identifier) is a unique identifier used to identify objects
 * throughout the system.
 */
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

    /**
     * Registers a new object. This will create a new GUID and associate it with the object.
     * @param o the object to register
     * @return the GUID associated with the object
     */
    public static GUID register(Object o) {
        GUID guid = new GUID();
        guid.guid = nextGUID++;
        guidMap.put(guid, o);
        return guid;
    }

    /**
     * Returns the object associated with the given GUID.
     *
     * @throws ClassCastException if the object is not of the given class
     *
     * @param guid the GUID to get the object for
     * @param clazz the class of the object to get
     * @return the object associated with the GUID
     */
    public static @NotNull Object get(GUID guid, Class<?> clazz) {
        Object o = guidMap.get(guid);
        if (!clazz.isInstance(o)) {
            throw new ClassCastException("Object is not of type " + clazz.getName());
        }
        return o;
    }
}
