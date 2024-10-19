package wrnkt.aoc.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public interface Day extends Runnable {
    public int number();
    public String desc();

    public default BufferedReader getInput() {
        String fName = String.format("2023/%d.txt", number());
        InputStream is = getClass().getClassLoader().getResourceAsStream(fName);
        return new BufferedReader(new InputStreamReader(is));
    }
}
