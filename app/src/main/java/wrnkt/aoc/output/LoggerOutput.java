package wrnkt.aoc.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;

public class LoggerOutput extends DayOutput {
    public static final Logger log = LoggerFactory.getLogger(LoggerOutput.class);

    private Logger dayLog;

    Class<? extends Day> dayClazz;

    public LoggerOutput(Day day) {
        registerDay(day);
    }

    public void registerDay(Day day) {
        dayClazz = day.getClass();
        dayLog = LoggerFactory.getLogger(dayClazz);
        super.registerDay(day);
    }

    public void submit(String s) {
        log.info(s);
    }

    public boolean isReady() {
        return (dayClazz != null) && (dayLog != null);
    }

    @Override
    public String toString() {
        return "LoggerOutput";
    }

}
