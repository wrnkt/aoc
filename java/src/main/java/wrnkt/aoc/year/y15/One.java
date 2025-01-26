package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import aoc.framework.Day;

public class One extends Day {

    public static final char UP_INDICATOR = '(';
    public static final char DOWN_INDICATOR = ')';

    @Override
    public void solution(BufferedReader reader) {
        List<Character> data = readData(reader);
        partOne(data);
        partTwo(data);
    }

    private void partTwo(List<Character> data) {
        print("Part Two:");
        long basementEntry = findBasementEntry(data);
        print(String.format("Santa enters the basement at instruction #%d", basementEntry));
    }

    private long findBasementEntry(List<Character> data) {
        long floor = 0;
        Character instruction;
        for (int i = 0; i < data.size(); i++) {
            instruction = data.get(i);
            switch (instruction) {
                case UP_INDICATOR:   floor++;
                    break;
                case DOWN_INDICATOR: floor--;
                    break;
                default: break;
            }
            if (floor == -1) return (i+1);
        }
        return 0;
    }

    private void partOne(List<Character> data) {
        print("Part One:");
        long floor = getFloor(data);
        print(String.format("The instructions take Santa to floor %d", floor));
    }

    private long getFloor(List<Character> data) {
        Map<Character, Long> counts = data.stream()
                                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        long upCount = counts.get(UP_INDICATOR);
        long downCount = counts.get(DOWN_INDICATOR);

        return upCount - downCount;
    }

    private List<Character> readData(BufferedReader reader) {
        Stream<Character> cStream = reader.lines()
                                .flatMap(line -> line.chars()
                                                    .mapToObj(c -> (char) c)
                                );
        return cStream.toList();
    }

}
