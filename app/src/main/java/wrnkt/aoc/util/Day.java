package wrnkt.aoc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.AutoChallengeRunner;
import wrnkt.aoc.output.DayOutput;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public abstract class Day implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(Day.class);

    private DayOutput output;

    public Optional<String> desc() {
        return Optional.empty();
    }

    public void print(String s) {
        output.submit(s);
    }

    /**
     * Solve the problem in this function. The {@link BufferedReader} provides access to the
     * content of the the puzzle's auto-loaded input file.
     *
     * @param   br  the puzzle's input
     */
    public abstract void solution(BufferedReader br);

    /**
     * Called by the {@link AutoChallengeRunner}, running the provided solution against the
     * puzzle's input.
     */
    @Override
    public void run() {
        var br = getInputReader();
        if (br.isEmpty()) {
            log.error("failed to load input for {}", dayName());
            return;
        }
        if (getOutput() == null) {
            log.error("No output set for Day.");
            return;
        }
        solution(br.get());
    }

    public String dayName() {
        var calculatedDayName = getSpelledDay();
        if (calculatedDayName.isEmpty()) {
            log.error("Could not determine proper day name, defaulting to class name");
            return this.getClass().getSimpleName();
        }
        return calculatedDayName.get();
    }

    public Optional<String> inputFileName() {
        var calculatedDay = getNumericalDay();
        var calculatedYear = getYear();

        if (calculatedDay.isEmpty() || calculatedYear.isEmpty()) return Optional.empty();

        var path = String.format("%d/%d.txt", calculatedYear.get(), calculatedDay.get());
        return Optional.of(path);
    }

    public Optional<BufferedReader> getInputReader() {
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

    public Optional<Integer> getNumericalDay() {
        var spelledDay = getSpelledDay();
        if (spelledDay.isEmpty()) return Optional.empty();

        return Optional.of(Numbers.spellingToDigit(spelledDay.get()));
    }

    // TODO: calculate this on first access and store in a member.
    //       return from member on consecutive accesses.
    public Optional<String> getSpelledDay() {
        String className = this.getClass().getName();

        int lastPeriodIdx = className.lastIndexOf('.');
        if (lastPeriodIdx == -1) {
            return Optional.empty();
        }

        String dateOfMonth = className.substring(lastPeriodIdx+1).toLowerCase();

        return Optional.ofNullable(dateOfMonth);
    }

    public Optional<Integer> getYear() {
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

    public DayOutput getOutput() { return this.output; }
    public void setOutput(DayOutput output) { this.output = output; }

}
