package aoc.framework.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public interface Streams {

    public static <A, B> Stream<Pair<A, B>> zip(Stream<A> streamA, Stream<B> streamB) {
        Iterator<A> iteratorA = streamA.iterator();
        Iterator<B> iteratorB = streamB.iterator();

        List<Pair<A, B>> list = new ArrayList<>();

        while (iteratorA.hasNext() && iteratorB.hasNext()) {
            A elemA = iteratorA.next();
            B elemB = iteratorB.next();
            list.add(new Pair<>(elemA, elemB));
        }
        return list.stream();
    }

}
