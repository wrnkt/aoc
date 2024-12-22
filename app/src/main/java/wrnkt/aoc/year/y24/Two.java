package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import aoc.framework.Day;

public class Two extends Day {

    @Override
    public void solution(BufferedReader reader) {
        var data = readData(reader);
        print(String.format("lines read: %d", data.size()));

        partOne(data);
        partTwo(data);
    }

    private void partTwo(List<List<Integer>> data) {
        print("Part Two:");
        var safeReports = data.stream().filter(this::isSafeUpdated).toList();
        print(String.format("number of safe reports: %d", safeReports.size()));
    }

    private void partOne(List<List<Integer>> data) {
        print("Part One:");
        var safeReports = data.stream().filter(this::isSafe).toList();
        print(String.format("number of safe reports: %d", safeReports.size()));
    }

    private boolean isSafe(List<Integer> reading) {
        boolean ascending = false;
        boolean didSetAscending = false;

        Integer prev = null;
        Integer current = null;
        
        for (int i = 0; i < reading.size(); i++) {
            current = reading.get(i);

            if (i > 0) {
                var diff = current - prev;
                if (Math.abs(diff) > 3 || Math.abs(diff) < 1) return false;
                if (diff > 0)  {
                    if (!ascending && didSetAscending) return false;
                    ascending = true;
                    didSetAscending = true;
                }
                if (diff < 0) {
                    if (ascending && didSetAscending) return false;
                    ascending = false;
                    didSetAscending = true;
                }
            }

            prev = current;
        }
        return true;
    }

    private boolean isSafeUpdated(List<Integer> reading) {
        boolean ascending = false;
        boolean didSetAscending = false;

        Integer prev = null;
        Integer current = null;
        
        for (int i = 0; i < reading.size(); i++) {
            current = reading.get(i);

            if (i > 0) {
                var diff = current - prev;
                if (Math.abs(diff) > 3 || Math.abs(diff) < 1) {
                    var revisedReading = new ArrayList<>(reading);
                    revisedReading.remove(i);
                    return isSafe(revisedReading);
                }{}
                if (diff > 0)  {
                    if (!ascending && didSetAscending) {
                        var revisedReading = new ArrayList<>(reading);
                        revisedReading.remove(i);
                        return isSafe(revisedReading);
                    }
                    ascending = true;
                    didSetAscending = true;
                }
                if (diff < 0) {
                    if (ascending && didSetAscending) {
                        var revisedReading = new ArrayList<>(reading);
                        revisedReading.remove(i);
                        return isSafe(revisedReading);
                    }
                    ascending = false;
                    didSetAscending = true;
                }
            }
            prev = current;
        }
        return true;
    }

    private List<List<Integer>> readData(BufferedReader reader) {
        List<List<Integer>> data = new ArrayList<>();

        reader.lines().forEach((line) -> {
            var parsedLine = new ArrayList<Integer>();
            String[] words = line.split("\\s+");
            for (String word : words) {
                parsedLine.add(Integer.parseInt(word));
            }
            assert(parsedLine.size() == 5);
            data.add(parsedLine);
        });

        return data;
    }

}
