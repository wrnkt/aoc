package wrnkt.aoc.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOutput extends DayOutput {
    public static final Logger log = LoggerFactory.getLogger(ConsoleOutput.class);

    public void submit(String s) {
        System.out.println(s);
    }

    public boolean isReady() {
        return true;
    }

    @Override
    public String toString() {
        return "ConsoleOutput";
    }

}
