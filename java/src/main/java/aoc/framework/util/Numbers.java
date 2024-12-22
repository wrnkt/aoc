package aoc.framework.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Numbers {

    public static final Map<Integer, String> spelledNumberMap = new HashMap<>();

    static {
        spelledNumberMap.put(1, "one");
        spelledNumberMap.put(2, "two");
        spelledNumberMap.put(3, "three");
        spelledNumberMap.put(4, "four");
        spelledNumberMap.put(5, "five");
        spelledNumberMap.put(6, "six");
        spelledNumberMap.put(7, "seven");
        spelledNumberMap.put(8, "eight");
        spelledNumberMap.put(9, "nine");
        spelledNumberMap.put(10, "ten");
        spelledNumberMap.put(11, "eleven");
        spelledNumberMap.put(12, "twelve");
        spelledNumberMap.put(13, "thirteen");
        spelledNumberMap.put(14, "fourteen");
        spelledNumberMap.put(15, "fifteen");
        spelledNumberMap.put(16, "sixteen");
        spelledNumberMap.put(17, "seventeen");
        spelledNumberMap.put(18, "eighteen");
        spelledNumberMap.put(19, "nineteen");
        spelledNumberMap.put(20, "twenty");
        spelledNumberMap.put(21, "twentyone");
        spelledNumberMap.put(22, "twentytwo");
        spelledNumberMap.put(23, "twentythree");
        spelledNumberMap.put(24, "twentyfour");
        spelledNumberMap.put(25, "twentyfive");
        spelledNumberMap.put(26, "twentysix");
        spelledNumberMap.put(27, "twentyseven");
        spelledNumberMap.put(28, "twentyeight");
        spelledNumberMap.put(29, "twentynine");
        spelledNumberMap.put(30, "thirty");
    }

    public static String digitToSpelling(Integer i) {
        return spelledNumberMap.get(i);
    }

    public static Integer spellingToDigit(String s) {
        var i = spelledNumberMap.entrySet().stream()
                .filter((var entry) -> s.equals(entry.getValue()))
                .map((var entry) -> entry.getKey())
                .findFirst();
        return i.get();
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
