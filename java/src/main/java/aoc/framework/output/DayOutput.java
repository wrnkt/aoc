package aoc.framework.output;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aoc.framework.Day;

public abstract class DayOutput {
    public static final Logger log = LoggerFactory.getLogger(DayOutput.class);

    private Optional<Day> registeredDay = Optional.empty();

    public void registerDay(Day day) {
        if (!isReady()) {
            log.error("not ready for output: failed to register Day");
            log.info("defaulting to SystemOutput");
            day.setOutput(new SystemOutOutput(day));
        }
        day.setOutput(this);
        registeredDay = Optional.of(day);
    };

    public boolean isRegistered() {
        return registeredDay.isPresent();
    }

    public abstract boolean isReady();

    public abstract void submit(String s);

}
