package wrnkt.aoc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public interface Day extends Runnable {
    public static final Logger log = LoggerFactory.getLogger(Day.class);

    public default Optional<String> desc() {
        return Optional.empty();
    }

    public default String dayName() {
        String dayName = null;
        try {
            dayName = Util.getStringDateOfMonth(this);
        } catch (Exception e) {
            log.error("Could not determine proper day name.");
            dayName = this.getClass().getSimpleName();
            log.error("Defaulting to className {}", dayName);
        }
        return dayName;
    }

    public default String inputFileName() {
        try {
            return String.format("%d/%d.txt", Util.getYear(this), Util.getNumericalDateOfMonth(this));
        } catch (Exception e) {
            log.error("Failed to get input file name.");
        }
        return null;
    }

    public default BufferedReader getInputReader() throws Exception {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(inputFileName());
            return new BufferedReader(new InputStreamReader(is));
        } catch (Exception e) {
            log.error("Could not get reader for input.");
        }
        return null;
    }

}
