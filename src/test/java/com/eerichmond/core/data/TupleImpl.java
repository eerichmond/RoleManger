package com.eerichmond.core.data;

import com.mysema.query.Tuple;
import com.mysema.query.types.Expression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

public class TupleImpl implements Tuple {
    private final Object[] items;

    public TupleImpl(Object... items) {
        this.items = items;
    }

    @Override
    public <T> T get(int index, Class<T> type) {
        return (T) items[index];
    }

    @Override
    public <T> T get(Expression<T> expr) {
        throw new NotImplementedException();
    }

    @Override
    public int size() {
        return items.length;
    }

    @Override
    public Object[] toArray() {
        return items;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof Tuple && Arrays.equals(items, ((Tuple) obj).toArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    @Override
    public String toString() {
        return Arrays.toString(items);
    }
}
