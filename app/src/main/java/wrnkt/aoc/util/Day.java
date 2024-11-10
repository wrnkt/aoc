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

    public void solution(BufferedReader br);

    public default void run() {
        var br = getInputReader();
        if (br.isEmpty()) {
            log.error("failed to load input for {}", dayName());
            return;
        }
        solution(br.get());
    }

    public default String dayName() {
        String dayName = null;

        var calculatedDayName = getSpelledDay();
        if (calculatedDayName.isEmpty()) {
            log.error("Could not determine proper day name.");
            dayName = this.getClass().getSimpleName();
            log.error("Defaulting to className {}", dayName);
        }
        return dayName;
    }

    public default Optional<String> inputFileName() {
        var calculatedDay = getNumericalDay();
        var calculatedYear = getYear();

        if (calculatedDay.isEmpty() || calculatedYear.isEmpty()) return Optional.empty();

        var path = String.format("%d/%d.txt", calculatedYear.get(), calculatedDay.get());
        return Optional.of(path);
    }

    public default Optional<BufferedReader> getInputReader() {
        var foundPath = inputFileName();
        if (foundPath.isEmpty()) return Optional.empty();

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(foundPath.get());
            return Optional.of(new BufferedReader(new InputStreamReader(is)));
        } catch (Exception e) {
            log.error("Could not get reader for input.");
        }
        return Optional.empty();
    }

    /* ----------------- */
    /*      UTILITY      */
    /* ----------------- */

    public default Optional<Integer> getNumericalDay() {
        var spelledDay = getSpelledDay();
        if (spelledDay.isEmpty()) return Optional.empty();

        return Optional.of(Numbers.spellingToDigit(spelledDay.get()));
    }

    public default Optional<String> getSpelledDay() {
        String className = this.getClass().getName();

        int lastPeriodIdx = className.lastIndexOf('.');
        if (lastPeriodIdx == -1) {
            return Optional.empty();
        }

        String dateOfMonth = className.substring(lastPeriodIdx+1).toLowerCase();

        return Optional.ofNullable(dateOfMonth);
    }

    public default Optional<Integer> getYear() {
        String className = this.getClass().getName();

        int lastPeriodIdx = className.lastIndexOf('.');
        if (lastPeriodIdx == -1) {
            return Optional.empty();
        }
        int secondToLastPeriodIdx = className.lastIndexOf('.', lastPeriodIdx - 1);
        if (secondToLastPeriodIdx == -1) {
            return Optional.empty();
        }

        String yearStr = className.substring(secondToLastPeriodIdx+1, lastPeriodIdx);

        if (yearStr.chars().noneMatch(Character::isDigit)) {
            return Optional.of(Numbers.spellingToDigit(yearStr));
        } else {
            yearStr = yearStr.replaceAll("[a-zA-Z]", "");
            int year = Integer.parseInt(yearStr);
            if (year < 2000) year += 2000;
            return Optional.of(year);
        }
    }

}
