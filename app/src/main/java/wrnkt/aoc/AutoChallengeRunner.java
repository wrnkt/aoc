package wrnkt.aoc;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;
import wrnkt.aoc.util.Formatter;
import wrnkt.aoc.util.Numbers;

public class AutoChallengeRunner {
    
    public static final Logger log = LoggerFactory.getLogger(AutoChallengeRunner.class);


    public static final String YEAR_PACKAGE = "wrnkt.aoc.year";

    public static final Set<Integer> DEFAULT_YEARS = Set.of(2023);

    public Map<Integer,Set<Integer>> puzzleList = new HashMap<>();

    public Set<Class<? extends Day>> loadedDays = new HashSet<>();


    public AutoChallengeRunner(Map<Integer,Set<Integer>> puzzleList) {
        setPuzzleList(puzzleList);
    }


    /* ----------------- */
    /*       SETUP       */
    /* ----------------- */

    public void addPuzzles(int year, Integer... days) {
        var yearsPuzzles = getPuzzleList().getOrDefault(year, new HashSet<>());
        yearsPuzzles.addAll(Arrays.asList(days));
        getPuzzleList().put(year, yearsPuzzles);
    }

    public static AutoChallengeRunnerBuilder setup() {
        return new AutoChallengeRunnerBuilder();
    }

    public static class AutoChallengeRunnerBuilder {

        private Map<Integer,Set<Integer>> puzzleList = new HashMap<>();

        public AutoChallengeRunner build() {
            log.info("-----------------------------");
            log.info("Building AutoChallengeRunner");
            log.info("Puzzles:");
            puzzleList.entrySet().stream()
                .forEach((var entry) -> log.info("\t{}", Formatter.formatYearOverview(entry)));
            log.info("-----------------------------");
            var runner = new AutoChallengeRunner(puzzleList);
            return runner;
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

    }
    /* ----------------- */
    /*      RUNNING      */
    /* ----------------- */

    public void runAll() {
        Day day;
        getLoadedDays().forEach((Class<? extends Day> loadedDay) -> {
            try {
                run(loadedDay.getDeclaredConstructor().newInstance());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private void run(Day day) {
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
        String spelledDate = Numbers.spelledNumber(day);
        // log.info("day({}) -> spelledDate({})", day, spelledDate);
        return Formatter.capitalize(spelledDate);
    }

    private void loadDays() {
        var loadedDays = _loadDays(getPuzzleList());
        log.info("loaded {} puzzle classes.", loadedDays.size());
        setLoadedDays(loadedDays);
    }

    private String buildFQName(Integer year, Integer day) {
        String fqName = String.format("%s.%s.%s", YEAR_PACKAGE, yearComponent(year), dayComponent(day));
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
                        // NOTE: couldn't load day
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

}
