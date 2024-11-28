package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wrnkt.aoc.util.Day;
import wrnkt.aoc.util.Pair;

public class Eight extends Day {

    @Override
    public void solution(BufferedReader reader) {
        MapData mapData = parseData(reader);
        print(String.format("Map size: %d entries", mapData.getMap().size()));

        //int steps = calculateSteps(mapData);
    }

    public int calculateSteps(MapData mapData) {
        int steps = 0;
        var map = mapData.getMap();
        var directions = mapData.getDirections();

        String lookupLocation = "AAA";
        int i = 0;
        while (true) {
            var direction = directions[i];

            Pair<String, String> dirOptions = map.get(lookupLocation);

            switch (direction) {
                case 'R':
                lookupLocation = dirOptions.getFirst();
                    break;
                case 'L':
                lookupLocation = dirOptions.getSecond();
                    break;
                default:
                    throw new RuntimeException("THIS SHOULDN'T HAPPEN");
            }
            steps++;

            if (lookupLocation == "ZZZ") break;

            ++i;
            if (i == directions.length) i = 0;
        }
        return steps;
    }

    public MapData parseData(BufferedReader reader) {
        MapData data = new MapData();

        String line;
        boolean firstRow = true;
        try {
            while ((line = reader.readLine()) != null) {
                if (firstRow) {
                    data.setDirections(parseDirections(line));
                    firstRow = false;
                } else {
                    if (line.isBlank()) continue;

                    var mapLine = parseMapLine(line);
                    data.getMap().put(mapLine.getFirst(), mapLine.getSecond());
                }
            }
        } catch (IOException e) {
            print("Failed to parse data.");
        }

        return data;
    }

    public Pair<String, Pair<String, String>> parseMapLine(String line) {
        String regex = "(\\w+) = \\((\\w+), (\\w+)\\)";
        Matcher matcher = Pattern.compile(regex).matcher(line);

        if (!matcher.find()) {
            print("ERRORED");
            print(line);
            throw new IllegalArgumentException();
        }

        String loc = matcher.group(1);
        String left = matcher.group(2);
        String right = matcher.group(3);

        return new Pair<>(loc, new Pair<>(left, right));
    }

    public char[] parseDirections(String line) {
        return line.toCharArray();
    }

    public class MapData {
        private Map<String, Pair<String, String>> map = new HashMap<>();
        private char[] directions = {};

        public MapData() {}

        public MapData(Map<String, Pair<String, String>> map, char[] directions) {
            this.map = map;
            this.directions = directions;
        }

        public Map<String, Pair<String, String>> getMap() { return this.map; }
        public void setMap(Map<String, Pair<String, String>> map) { this.map = map; }

        public char[] getDirections() { return this.directions; }
        public void setDirections(char[] directions) { this.directions = directions; }
    }

}
