package aoc.framework;

import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    public static final Logger log = LoggerFactory.getLogger(Config.class);

    Properties properties = new Properties();

    private String outputType = "console";
    private String yearPackage;

    public Map<Integer,Set<Integer>> puzzleList = new HashMap<>();

    public Config() {
        loadProperties();
    }

    public void loadProperties() {
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
            var readOutputType = properties.getProperty("runner.output");
            this.outputType = readOutputType;

            var readYearPackage = properties.getProperty("runner.scan.package");
            this.yearPackage = readYearPackage;

            loadPuzzles();
        } catch (IOException e) {
            log.error("Failed to load properties.");
            log.error(e.getMessage());
        }
    }

    public void loadPuzzles() {
        for (int year = 2000; year < 2030; year++) {
            var readPuzzlesForYear = properties.getProperty(String.format("runner.year.%d", year));
            if (readPuzzlesForYear == null) continue;
            var days = Arrays.stream(readPuzzlesForYear.split(","))
                            .mapToInt((s) -> Integer.parseInt(s)).boxed()
                            .collect(Collectors.toUnmodifiableSet());
            puzzleList.put(year, days);
        }
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getYearPackage() {
        return yearPackage;
    }

    public void setYearPackage(String yearPackage) {
        this.yearPackage = yearPackage;
    }

    public Map<Integer, Set<Integer>> getPuzzleList() {
        return puzzleList;
    }

    public void setPuzzleList(Map<Integer, Set<Integer>> puzzleList) {
        this.puzzleList = puzzleList;
    }

    
}
