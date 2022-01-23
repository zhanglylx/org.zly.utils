package org.zly.utils.random;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ZlyRandom<T> {
    T nextRandom();

    @SuppressWarnings("unchecked")
    default T nextRandom(int number) {
        try {
            String string = (String) nextRandom();
            return (T) string.substring(0, number);
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException();
        }
    }

    default List<T> nextRandoms(int number) {
        Set<T> s = new HashSet<>(number);
        while (s.size() < number) {
            s.add(nextRandom());
        }
        return new ArrayList<>(s);
    }
}
