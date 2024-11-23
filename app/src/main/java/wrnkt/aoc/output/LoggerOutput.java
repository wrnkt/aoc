package wrnkt.aoc.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;

public class LoggerOutput implements DayOutput {
    public static final Logger log = LoggerFactory.getLogger(LoggerOutput.class);

    private Logger dayLog;

    Class<? extends Day> dayClazz;

    public void registerDay(Day day) {
        dayClazz = day.getClass();
        dayLog = LoggerFactory.getLogger(dayClazz);
    }

    public void submit(String s) {
        log.info(s);
    }

}
