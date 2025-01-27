package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import aoc.framework.Day;

public class Four extends Day {

    private final MessageDigest md;

    private static final String DEFAULT_KEY = "yzbqklnj";

    private final int PART = 2;

    public Four() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
    }

    @Override
    public void solution(BufferedReader reader) {
        String key = getKey(reader);

        long solution = 0;
        while(true) {
            String hash = hash(key, solution);

            if (PART == 1 && hashPassesOne(hash)) {
                printInfo(key, solution);
                break;
            }
            if (PART == 2 && hashPassesTwo(hash)) {
                printInfo(key, solution);
                break;
            }
            solution++;
        }
    }

    private String getKey(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return DEFAULT_KEY;
        }
    }

    private void printInfo(String key, long num) {
        print(String.format("With the key %s & decimal %d:", key, num));
        print(String.format("The hash of %s%d = %s", key, num, hash(key, num)));
    }

    private String hash(String key, long num) {
        String input = String.format("%s%d", key, num);
        md.update(input.getBytes());
        byte[] digest = md.digest();
        return hexify(digest);
    }

    private String hexify(byte[] digest) {
        StringBuilder hexStr = new StringBuilder();
        for (byte b : digest) {
            hexStr.append(String.format("%02x", b));
        }
        return hexStr.toString();
    }

    private boolean hashPassesOne(String hash) {
        return hash.startsWith("00000");
    }

    private boolean hashPassesTwo(String hash) {
        return hash.startsWith("000000");
    }

}
