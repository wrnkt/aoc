package aoc.framework.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aoc.framework.Day;

public class ConsoleOutput extends DayOutput {
    public static final Logger log = LoggerFactory.getLogger(ConsoleOutput.class);

    public ConsoleOutput(Day day) {
        registerDay(day);
    }

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
