package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.framework.Day;

public class Two extends Day {

    public static final Pattern PRESENT_DIMENSION_PATTERN = Pattern.compile("(\\d+)x(\\d+)x(\\d+)");

    @Override
    public void solution(BufferedReader reader) {
        List<String> data = readData(reader);
        List<Present> presents = parsePresents(data);
        print(String.format("%d presents parsed", presents.size()));
        partOne(presents);
        partTwo(presents);
    }

    private void partOne(List<Present> presents) {
        print("Part One:");
        long sqFtWrappingPaper = presents.stream()
                                    .mapToLong(p -> p.wrappingArea())
                                    .sum();
        print(String.format("wrapping paper required: %d sqft", sqFtWrappingPaper));
    }

    private void partTwo(List<Present> presents) {
        print("Part Two:");
        long ftRibbon = presents.stream()
                            .mapToLong(p -> p.ribbonLength())
                            .sum();
        print(String.format("ribbon required: %d ft", ftRibbon));
    }

    public List<Present> parsePresents(List<String> data) {
        List<Present> presents = new ArrayList<>();

        for (String s : data) {
            Matcher m = PRESENT_DIMENSION_PATTERN.matcher(s);
            if (m.matches()) {
                presents.add(
                    new Present(
                        Integer.parseInt(m.group(1)),
                        Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3))
                    )
                );
            } else {
                log.error("Failed to parse present dimensions for String: {}", s);
            }
        }
        return presents;
    }

    public List<String> readData(BufferedReader reader) {
        return reader.lines().collect(Collectors.toList());
    }

    record Present(int l, int w, int h) {

        public long ribbonLength() {
            List<Integer> doubledSides = Arrays.asList(l, w, h).stream()
                                            .map(n -> 2*n)
                                            .collect(Collectors.toList());
            doubledSides.remove(Collections.max(doubledSides));

            return (doubledSides.get(0) + doubledSides.get(1)) + (l*w*h);
        }

        public long wrappingArea() {
            return surfaceArea() + smallestSideArea();
        }

        public long smallestSideArea() {
            List<Integer> sides = new ArrayList<>(Arrays.asList(l, w, h));
            sides.remove(Collections.max(sides));
            return sides.get(0) * sides.get(1);
        }

        public long surfaceArea() {
            return (2*l*w) + (2*w*h) + (2*h*l);
        }
    }
}
