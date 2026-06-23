package aoc.common.io;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class BufferAccess {

    private BufferAccess() {}

    public static List<String> listStrings(BufferedReader br) {
        return br.lines().toList();
    }

    public static List<Integer> listInts(BufferedReader br) {
        return br.lines()
            .map(Integer::parseInt)
            .toList();
    }

    public static Stream<String> streamStrings(BufferedReader br) {
        return br.lines();
    }

    public static IntStream streamInts(BufferedReader br) {
        return br.lines()
            .mapToInt(Integer::parseInt);
    }

}
