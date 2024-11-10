package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import wrnkt.aoc.util.Day;

public class One implements Day {

    public Optional<String> desc() {
        return Optional.of("Something about snow or something...");
    }

    private static final int BASE = 10;
    private static Map<Character[], Integer> writtenNumbers = new HashMap<>();

    static {
        writtenNumbers.put(new Character[] { 'o', 'n', 'e' }, 1);
        writtenNumbers.put(new Character[] { 't', 'w', 'o' }, 2);
        writtenNumbers.put(new Character[] { 't', 'h', 'r', 'e', 'e' }, 3);
        writtenNumbers.put(new Character[] { 'f', 'o', 'u', 'r' }, 4);
        writtenNumbers.put(new Character[] { 'f', 'i', 'v', 'e' }, 5);
        writtenNumbers.put(new Character[] { 's', 'i', 'x' }, 6);
        writtenNumbers.put(new Character[] { 's', 'e', 'v', 'e', 'n' }, 7);
        writtenNumbers.put(new Character[] { 'e', 'i', 'g', 'h', 't' }, 8);
        writtenNumbers.put(new Character[] { 'n', 'i', 'n', 'e' }, 9);
    }

    public static Character[] toCharacterArray(char[] cs) {
        int len = cs.length;
        Character[] cArr = new Character[len];
        for (int i = 0; i < len; ++i) {
            cArr[i] = cs[i];
        }
        return cArr;
    }

    public static <T> T[] subArray(T[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }

    private static Character firstNumber(final String s) {
        char[] cs = s.toCharArray();

        for (int i = 0; i < cs.length; ++i) {
            if (Character.isDigit(cs[i])) {
                return cs[i];
            } else {
                int remainingCharacters = cs.length - i;
                Character[] refNumber;
                Character[] checkingNumber;
                for (Map.Entry<Character[], Integer> entry : writtenNumbers.entrySet()) {
                    refNumber = entry.getKey();
                    if (refNumber.length <= remainingCharacters) {
                        checkingNumber = subArray(toCharacterArray(cs), i, (i + refNumber.length - 1));
                        if (Arrays.equals(refNumber, checkingNumber)) {
                            return Character.forDigit(entry.getValue(), BASE);
                        }
                    }
                }
            }
        }
        return null;
    }

    private static Character lastNumber(final String s) {
        char[] cs = s.toCharArray();

        for (int i = cs.length - 1; i >= 0; --i) {
            if (Character.isDigit(cs[i])) {
                return cs[i];
            } else {
                int remainingCharacters = cs.length - i;
                Character[] refNumber;
                Character[] checkingNumber;
                for (Map.Entry<Character[], Integer> entry : writtenNumbers.entrySet()) {
                    refNumber = entry.getKey();
                    if (refNumber.length <= remainingCharacters) {
                        checkingNumber = subArray(toCharacterArray(cs), i, (i + refNumber.length - 1));
                        if (Arrays.equals(refNumber, checkingNumber)) {
                            return Character.forDigit(entry.getValue(), BASE);
                        }
                    }
                }
            }
        }
        return null;
    }

    private static Integer getCalibrationValue(final String s) {
        Character first = firstNumber(s);
        Character last = lastNumber(s);

        if (first == null || last == null) {
            System.out.println("[ERROR]: WTF NOT SUPPOSED TO HAPPEN");
        }

        return Integer.parseInt(String.format("%s%s", first, last));
    }

    @Override
    public void solution(BufferedReader reader) {
        ArrayList<Integer> calValues = new ArrayList<>();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Integer c = getCalibrationValue(line);
                System.out.println(String.format("%s -> %d", line, c));
                calValues.add(c);
            }
        } catch (Exception e) {
            System.out.println("Failed to run this challenge.");
            System.out.println(String.format("%s", inputFileName()));
        }
        int total = calValues.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println(String.format("[INFO]: Total: %d", total));
    }
}
