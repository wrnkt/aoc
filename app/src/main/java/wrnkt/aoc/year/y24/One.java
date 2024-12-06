package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import wrnkt.aoc.util.Day;
import wrnkt.aoc.util.Pair;

public class One extends Day {

    @Override
    public void solution(BufferedReader reader) {
        Pair<List<Integer>, List<Integer>> data = readData(reader);
        print(String.format("lines of data read: %d", data.getFirst().size()));

        partOne(data);
        partTwo(data);
    }

    private void partOne(Pair<List<Integer>, List<Integer>> data) {
        print("> Part One");

        List<Integer> sortedLeftList = data.getFirst().stream().sorted().toList();
        List<Integer> sortedRightList = data.getSecond().stream().sorted().toList();

        int sum = IntStream.range(0, sortedLeftList.size())
                    .map((idx) -> Math.abs(sortedLeftList.get(idx) - sortedRightList.get(idx)))
                    .sum();

        print(String.format("SUM_OF_DIFFERENCES = %d", sum));
    }

    private void partTwo(Pair<List<Integer>, List<Integer>> data) {
        print("> Part Two");

        List<Integer> leftList = data.getFirst();
        List<Integer> rightList = data.getSecond();

        Map<Integer, Integer> similarityScoreMap = new HashMap<>();

        for (Integer num : leftList) {
            int occurrences = Collections.frequency(rightList, num);
            similarityScoreMap.put(num, occurrences);
        }

        long score = similarityScoreMap
                        .entrySet()
                        .stream()
                        .mapToLong((entry) -> entry.getKey() * entry.getValue())
                        .sum();

        print(String.format("SIMILARITY_SCORE = %d", score));
    }

    private Pair<List<Integer>, List<Integer>> readData(BufferedReader reader) {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        Pair<List<Integer>, List<Integer>> data = new Pair<>(leftList, rightList);

        reader.lines().forEach((line) -> {
            if (!line.isEmpty()) {
                String[] elements = line.split("\\s+");
                leftList.add(Integer.parseInt(elements[0]));
                rightList.add(Integer.parseInt(elements[1]));
            }
        });

        return data;
    }

}
