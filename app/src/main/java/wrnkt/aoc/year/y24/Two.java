package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import aoc.framework.Day;

public class Two extends Day {

    private List<List<Integer>> data = new ArrayList<>();

    @Override
    public void solution(BufferedReader reader) {
        var data = readData(reader);
        print(String.format("read %d lines", data.size()));
    }

    private List<List<Integer>> readData(BufferedReader reader) {
        List<List<Integer>> data = new ArrayList<>();

        reader.lines().forEach((line) -> {
            var parsedLine = new ArrayList<Integer>();
            String[] words = line.split("\\s+");
            for (String word : words) {
                parsedLine.add(Integer.parseInt(word));
            }
            data.add(parsedLine);
        });

        return data;
    }

}
