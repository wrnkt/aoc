package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import aoc.framework.Day;

public class Eight extends Day {

    @Override
    public void solution(BufferedReader reader) {
        List<String> strings = readData(reader);
        partOne(strings);
    }

    private void partOne(List<String> input) {
        Integer summedLength = input.stream().mapToInt(s -> s.length()).sum();
        System.out.printf("summedLength = %d\n", summedLength);
        Integer parsedSummedLength = input.stream().mapToInt(this::parsedLength).sum();
        System.out.printf("parsedSummedLength = %d\n", parsedSummedLength);

        Integer difference = summedLength - parsedSummedLength;
        System.out.printf("difference = %d\n", difference);
    }

    private int parsedLength(String s) {
        String parsed = parse(s);
        return parsed.length();
    }

    private String parse(String s) {
        int len = s.length();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        for (int i = 0; i < len;) {
            char current = s.charAt(i);
            if (i == 0 || i == (len - 1) && current == '"') {
                i++;
            } else if (current == '\\') {
                if ((i < (len - 2))) {
                    char next = s.charAt(i + 1);
                    if (next == '\\') {
                        out.write('\\');
                        i += 2;
                    }
                    if (next == '"') {
                        out.write('"');
                        i += 2;
                    }
                    if (next == 'x') {
                        String hex = s.substring(i + 2, i + 4);
                        int h = Integer.parseInt(hex, 16);
                        out.write((char) h);
                        i += 4;
                    }
                } else {
                }
            } else {
                out.write(current);
                i++;
            }
        }
        return out.toString(Charset.defaultCharset());
    }

    public List<String> readData(BufferedReader reader) {
        List<String> strings = reader.lines()
                                .collect(Collectors.toList());
        return strings;
    }
}
