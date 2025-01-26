package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.util.Map;
import java.util.stream.Collectors;

import aoc.framework.Day;

public class One extends Day {

    public static final Character UP_INDICATOR = '(';
    public static final Character DOWN_INDICATOR = ')';

    @Override
    public void solution(BufferedReader reader) {
        partOne(reader);
    }

    private void partOne(BufferedReader reader) {
        print("---- Part One ----");
        long floor = getFloor(reader);
        print(String.format("The instructions take Santa to floor %d", floor));
        print("------------------");
    }

    private long getFloor(BufferedReader reader) {
        Map<Character, Long> counts = reader.lines()
                                        .flatMap(line -> line.chars().mapToObj(c -> (char) c))
                                        .collect(Collectors.groupingBy(c -> c, Collectors.counting())); 

        long upCount = counts.get(UP_INDICATOR);
        long downCount = counts.get(DOWN_INDICATOR);

        return upCount - downCount;
    }
}
