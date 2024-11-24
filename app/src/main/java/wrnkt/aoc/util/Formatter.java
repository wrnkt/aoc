package wrnkt.aoc.util;

import java.util.Map;
import java.util.Set;
import java.util.List;

public class Formatter {

    public static String formatYearOverview(Map.Entry<Integer, Set<Integer>> entry) {
        var year = entry.getKey();
        var days = List.of(entry.getValue().toArray());

        if (days.size() == 0) return "No puzzles.";

        StringBuilder dayView = new StringBuilder();

        Integer prevNumber = (Integer) days.get(0);
        dayView.append(prevNumber);

        Integer currentNumber;
        for (int i = 1; i < days.size(); i++) {
            currentNumber = (Integer) days.get(i);
            if (currentNumber == prevNumber + 1) {
                if (dayView.charAt(dayView.length() - 1) != '-') {
                    dayView.append('-');
                } else {
                    if ((i == days.size() - 1))  {
                        dayView.append(currentNumber);
                    }
                }
            } else {
                if (dayView.charAt(dayView.length() - 1) == '-') {
                    dayView.append(prevNumber);
                }
                dayView.append(',');
                dayView.append(currentNumber);
            }
            prevNumber = currentNumber;
        }
        var overview = String.format("%d -> %s", year, dayView.toString());
        return overview;
    }

    public static String capitalize(String s) {
        if (s == null || s.isBlank()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
    
}
