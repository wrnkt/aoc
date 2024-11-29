package wrnkt.aoc;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.output.ConsoleOutput;
import wrnkt.aoc.output.DayOutput;
import wrnkt.aoc.output.FileOutput;
import wrnkt.aoc.output.LoggerOutput;
import wrnkt.aoc.util.Day;
import wrnkt.aoc.util.Formatter;
import wrnkt.aoc.util.Numbers;

public class AutoChallengeRunner {
    public static final Logger log = LoggerFactory.getLogger(AutoChallengeRunner.class);

    public static final String DEFAULT_YEAR_PACKAGE = "aoc.year";
    private String yearPackage = DEFAULT_YEAR_PACKAGE;

    public static final OutputType DEFAULT_OUTPUT_TYPE = OutputType.CONSOLE;
    private OutputType outputType = DEFAULT_OUTPUT_TYPE;

    public Map<Integer,Set<Integer>> puzzleList = new HashMap<>();
    public Set<Class<? extends Day>> loadedDays = new HashSet<>();

    public AutoChallengeRunner(String yearPackage, OutputType outputType, Map<Integer,Set<Integer>> puzzleList) {
        setYearPackage(yearPackage);
        setOutput(outputType);
        setPuzzleList(puzzleList);
        // System.out.println(getInfo());
    }

    public AutoChallengeRunner(OutputType outputType, Map<Integer,Set<Integer>> puzzleList) {
        this(DEFAULT_YEAR_PACKAGE, outputType, puzzleList);
    }

    public AutoChallengeRunner(Map<Integer,Set<Integer>> puzzleList) {
        this(DEFAULT_YEAR_PACKAGE, DEFAULT_OUTPUT_TYPE, puzzleList);
    }

    /* ----------------- */
    /*       SETUP       */
    /* ----------------- */

    enum OutputType { FILE, CONSOLE, LOG }

    public void addPuzzles(int year, Integer... days) {
        var yearsPuzzles = getPuzzleList().getOrDefault(year, new HashSet<>());
        yearsPuzzles.addAll(Arrays.asList(days));
        getPuzzleList().put(year, yearsPuzzles);
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        log.info("-----------------------------");
        sb.append("AutoChallengeRunner config: " + "\n");
        sb.append("\tyearPackage: " + getYearPackage() + "\n");
        sb.append("\toutputType: " + getOutput() + "\n");
        sb.append("\tPuzzles:\n");
        puzzleList.entrySet().stream()
            .forEach((var entry) -> sb.append("\t\t" + Formatter.formatYearOverview(entry) + "\n"));
        log.info("-----------------------------");
        return sb.toString();
    }

    public static AutoChallengeRunnerBuilder setup() {
        return new AutoChallengeRunnerBuilder();
    }

    public static class AutoChallengeRunnerBuilder {

        private Map<Integer,Set<Integer>> puzzleList = new HashMap<>();
        private Map<Integer,Set<Integer>> excludedPuzzleList = new HashMap<>();

        private String yearPackage;
        private OutputType outputType = OutputType.CONSOLE;

        public AutoChallengeRunner build() {
            applyExclusions();
            log.info("-----------------------------");
            log.info("Building AutoChallengeRunner");
            log.info("Puzzles:");
            puzzleList.entrySet().stream()
                .forEach((var entry) -> log.info("\t{}", Formatter.formatYearOverview(entry)));
            log.info("-----------------------------");
            var runner = new AutoChallengeRunner(yearPackage, outputType, puzzleList);
            return runner;
        }

        public AutoChallengeRunnerBuilder config(Config config) {
            setOutput(config.getOutputType());
            setYearPackage(config.getYearPackage());
            setPuzzleList(config.getPuzzleList());
            return this;
        }

        public AutoChallengeRunnerBuilder autoConfig() {
            var config = new Config();
            config(config);
            return this;
        }

        public AutoChallengeRunnerBuilder setYearPackage(String yearPackage) {
            this.yearPackage = yearPackage;
            return this;
        }

        public AutoChallengeRunnerBuilder setOutput(String typeString) {
            try {
                var type = OutputType.valueOf(typeString.toUpperCase());
                this.outputType = type;
            } catch (IllegalArgumentException e) {
                log.error("Illegal output type: {}", e.getMessage());
            }
            return this;
        }

        public AutoChallengeRunnerBuilder setPuzzleList(Map<Integer, Set<Integer>> puzzleList) {
            this.puzzleList = puzzleList;
            return this;
        }

        public AutoChallengeRunnerBuilder addPuzzle(Integer year, Integer... days) {
            var yearsPuzzles = puzzleList.getOrDefault(year, new HashSet<>());
            yearsPuzzles.addAll(Arrays.asList(days));
            puzzleList.put(year, yearsPuzzles);
            return this;
        }

        public AutoChallengeRunnerBuilder addPuzzle(Integer year, Integer day) {
            var newList = puzzleList.getOrDefault(year, new HashSet<>());
            newList.add(day);
            puzzleList.put(year, newList);
            return this;
        }

        public AutoChallengeRunnerBuilder addPuzzleRange(Integer year, Integer startInc, Integer endInc) {
            var puzzles = puzzleList.getOrDefault(year, new HashSet<>());
            var newPuzzles = IntStream.rangeClosed(startInc, endInc).boxed()
                .collect(Collectors.toSet());
            puzzles.addAll(newPuzzles);
            puzzleList.put(year, puzzles);
            return this;
        }

        public AutoChallengeRunnerBuilder excludePuzzle(Integer year, Integer day) {
            var excludedPuzzles = excludedPuzzleList.getOrDefault(year, new HashSet<>());
            excludedPuzzles.add(day);
            excludedPuzzleList.put(year, excludedPuzzles);
            return this;
        }

        private void applyExclusions() {
            for (Map.Entry<Integer, Set<Integer>> entry : puzzleList.entrySet()) {
                var year = entry.getKey();
                var puzzles = entry.getValue();
                var excludedPuzzles = excludedPuzzleList.get(year);
                if (excludedPuzzles != null) {
                    puzzles.removeAll(excludedPuzzles);
                    puzzleList.put(year, puzzles);
                }
            }
        }

    }
    /* ----------------- */
    /*      RUNNING      */
    /* ----------------- */

    public void runAll() {
        getLoadedDays().forEach((Class<? extends Day> loadedDay) -> {
            try {
                run(loadedDay.getDeclaredConstructor().newInstance());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private DayOutput initOutput(Day day, OutputType type) {
        switch (type) {
        case CONSOLE:   return new ConsoleOutput(day);
        case FILE:      return new FileOutput(day);
        case LOG:       return new LoggerOutput(day);
        default:        return new ConsoleOutput(day);
        }
    }

    private void run(Day day) {
        // handle multiple outputs
        initOutput(day, getOutput());
        
        log.info(">>>>> Day {} <<<<<", Formatter.capitalize(day.dayName()));
        day.desc().ifPresent((desc) -> {
            log.info("{}", desc);
        });
        log.info("-------------------");
        day.run();
        log.info("-------------------");
    }

    /* ----------------- */
    /*   CLASS LOADING   */
    /* ----------------- */

    private String yearComponent(Integer year) {
        Integer yearNum = year % 2000;
        return String.format("y%d", yearNum);
    }

    private String dayComponent(Integer day) {
        String spelledDate = Numbers.digitToSpelling(day);
        return Formatter.capitalize(spelledDate);
    }

    private void loadDays() {
        var loadedDays = _loadDays(getPuzzleList());
        log.info("loaded {} puzzle classes.", loadedDays.size());
        setLoadedDays(loadedDays);
    }

    private String buildFQName(Integer year, Integer day) {
        String fqName = String.format("%s.%s.%s", getYearPackage(), yearComponent(year), dayComponent(day));
        return fqName;
    }

    private Set<Class<? extends Day>> _loadDays(Map<Integer,Set<Integer>> puzzleList) {
        Set<Class<? extends Day>> days = new HashSet<>();
        puzzleList.entrySet().stream()
            .forEach((Map.Entry<Integer, Set<Integer>> entry) -> {
                Integer year = entry.getKey();
                for (Integer day : entry.getValue()) {
                    var fqName = buildFQName(year, day);
                    var loadedDayCls = loadDay(fqName);
                    loadedDayCls.ifPresentOrElse((Class<? extends Day> dayCls) -> {
                        days.add(dayCls);
                        log.info("loaded: {}", fqName);
                    }, () -> {
                        log.error("Failed to load day: {}", fqName);
                        // FAIL: couldn't load day
                    });
                }
            });
        return days;
    }

    private Optional<Class<? extends Day>> loadDay(String fqn) {
        // log.info("attempting to load {}", fqn);
        try {
            Class<?> clazz = getClass().getClassLoader().loadClass(fqn);
            if (Day.class.isAssignableFrom(clazz)) {
                return Optional.of(clazz.asSubclass(Day.class));
            } else {
                log.error("Class {} is not a valid Day", clazz.getName());
            }
        } catch (ClassNotFoundException e) {
            log.error("Could not find a class for {}", fqn);
        }
        return Optional.empty();
    }

    /* ----------------- */
    /* GETTERS & SETTERS */
    /* ----------------- */

    public Map<Integer, Set<Integer>> getPuzzleList() {
        return this.puzzleList;
    }

    private void setPuzzleList(Map<Integer, Set<Integer>> puzzleList) {
        this.puzzleList = puzzleList;
        loadDays();
    }

    public Set<Class<? extends Day>> getLoadedDays() {
        return this.loadedDays;
    }

    public void setLoadedDays(Set<Class<? extends Day>> puzzleList) {
        this.loadedDays = puzzleList;
    }

    public OutputType getOutput() {
        return this.outputType;
    }

    public void setOutput(OutputType type) {
        this.outputType = type;
    }

    public String getYearPackage() {
        return this.yearPackage;
    }

    public void setYearPackage(String yearPackage) {
        this.yearPackage = yearPackage;
    }

}
