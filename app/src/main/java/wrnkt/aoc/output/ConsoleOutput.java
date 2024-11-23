package wrnkt.aoc.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;

public class ConsoleOutput implements DayOutput {
    public static final Logger log = LoggerFactory.getLogger(ConsoleOutput.class);

    public void registerDay(Day day) {}

    public void submit(String s) {
        System.out.println(s);
    }

}
