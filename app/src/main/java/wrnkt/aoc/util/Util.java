package wrnkt.aoc.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Util {

    public static int getNumericalDateOfMonth(Day day) throws Exception {
        return (writtenNumbers.indexOf(getStringDateOfMonth(day)) + 1);
    }

    public static String getStringDateOfMonth(Day day) throws Exception {
        String className = day.getClass().getName();

        int lastPeriodIdx = className.lastIndexOf('.');
        if (lastPeriodIdx == -1) {
            throw new Exception(String.format("Could not get the day of month for %s", className));
        }

        String dateOfMonth = className.substring(lastPeriodIdx+1).toLowerCase();
        if (!writtenNumbers.contains(dateOfMonth)) {
            throw new Exception(String.format("Invalid day of month: %s", dateOfMonth));
        }

        return dateOfMonth;
    }

    public static int getYear(Day day) throws Exception {
        String className = day.getClass().getName();

        int lastPeriodIdx = className.lastIndexOf('.');
        if (lastPeriodIdx == -1) {
            throw new Exception(String.format("Could not get the year for %s", className));
        }
        int secondToLastPeriodIdx = className.lastIndexOf('.', lastPeriodIdx - 1);
        if (secondToLastPeriodIdx == -1) {
            throw new Exception(String.format("Could not get the year for %s", className));
        }

        String yearStr = className.substring(secondToLastPeriodIdx+1, lastPeriodIdx);

        if (yearStr.chars().noneMatch(Character::isDigit)) {
            return (writtenNumbers.indexOf(yearStr) + 1);
        } else {
            yearStr = yearStr.replaceAll("[a-zA-Z]", "");
            int year = Integer.parseInt(yearStr);
            if (year < 2000) year += 2000;
            return year;
        }
    }

    public static String capitalize(String s) {
        if (s == null || s.isBlank()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    public static final List<String> writtenNumbers = Arrays.asList(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "ten",
        "eleven",
        "twelve",
        "thirteen",
        "fourteen",
        "fifteen",
        "sixteen",
        "seventeen",
        "eighteen",
        "nineteen",
        "twenty",
        "twentyone",
        "twentytwo",
        "twentythree",
        "twentyfour",
        "twentyfive",
        "twentysix",
        "twentyseven",
        "twentyeight",
        "twentynine",
        "thirty"
    );
}
