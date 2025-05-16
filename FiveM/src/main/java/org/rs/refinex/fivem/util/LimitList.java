package org.rs.refinex.fivem.util;

import java.util.ArrayList;
import java.util.Collection;

public class LimitList<T> extends ArrayList<T> {
    private final int limit;

    public LimitList(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(T t) {
        if (size() >= limit && limit != -1)
            throw new RuntimeException("Limit reached");
        return super.add(t);
    }

    @Override
    public void add(int index, T element) {
        if (size() >= limit && limit != -1)
            throw new RuntimeException("Limit reached");
        super.add(index, element);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (size() + c.size() > limit && limit != -1)
            throw new RuntimeException("Limit reached");
        return super.addAll(index, c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (size() + c.size() > limit && limit != -1)
            throw new RuntimeException("Limit reached");
        return super.addAll(c);
    }
}
