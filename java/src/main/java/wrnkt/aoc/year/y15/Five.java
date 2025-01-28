package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aoc.framework.Day;

public class Five extends Day {

    @Override
    public void solution(BufferedReader reader) {
        List<String> strings = readData(reader);
        partOne(strings);
        partTwo(strings);
    }

    public void partOne(List<String> strings) {
        print("Part One:");
        long numberNice = strings.stream()
                            .filter(Nice::isNice)
                            .count();
        print(String.format("nice strings: %d", numberNice));
    }

    public void partTwo(List<String> strings) {
        print("Part Two:");
        long numberNice = strings.stream()
                            .filter(Nice2::isNice)
                            .count();
        print(String.format("nice strings: %d", numberNice));
    }

    public List<String> readData(BufferedReader reader) {
        List<String> strings = reader.lines()
                                .collect(Collectors.toList());
        print(String.format("strings read: %d", strings.size()));
        return strings;
    }

    class Nice {

        private static String[] naughtyStrings = new String[] { "ab", "cd", "pq", "xy" };

        private static final String VOWELS = "aeiou";

        public static boolean isNice(String s) {
            if (countVowels(s) < 3) return false;
            if (!anyLetterAppearsTwice(s)) return false;
            if (containsNaughtyString(s)) return false;
            return true;
        }

        private static boolean containsNaughtyString(String s) {
            return Arrays.stream(naughtyStrings)
                        .anyMatch(naughty -> s.contains(naughty));
        }

        private static boolean anyLetterAppearsTwice(String s) {
            return repeatingCharacters(s).length() > 0;
        }

        private static String repeatingCharacters(String s) {
            return IntStream.range(0, s.length() - 1)
                        .filter(i -> s.charAt(i) == s.charAt(i + 1))
                        .mapToObj(i -> String.valueOf(s.charAt(i)))
                        .collect(Collectors.joining());
        }

        private static int countVowels(String s) {
            return s.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> VOWELS.indexOf(c) != -1)
                .map(String::valueOf)
                .collect(Collectors.joining())
                .length();
        }

    }

    class Nice2 {

        private static final Pattern REPEATING_PATTERN = Pattern.compile("(.).\\1");

        public static boolean isNice(String s) {
            return hasNonOverlappingPair(s) && hasXyXPattern(s);
        }

        private static boolean hasXyXPattern(String s) {
            Matcher matcher = REPEATING_PATTERN.matcher(s);
            if (matcher.find()) return true;
            return false;

        }

        private static boolean hasNonOverlappingPair(String s) {
            for (int i = 0; i < s.length() - 2; i++) {
                String pair = getPair(s, i);
                int nextPairIdx = s.indexOf(pair, i+2);
                if (nextPairIdx != -1) return true;
            }
            return false;
        }

        private static String getPair(String s, int idx) {
            return s.substring(idx, idx + 2);
        }

    }

}
