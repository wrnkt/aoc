package wrnkt.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;
import wrnkt.aoc.util.Util;

public class ChallengeRunner {
    public static final Logger log = LoggerFactory.getLogger(ChallengeRunner.class);

    public final List<ChallengeYear> years = new ArrayList<>() {{
        add(new Y23());
    }};

    public void runAll() {
        for (ChallengeYear year : years) {
            runYear(year);
        }
    }

    public void runYear(ChallengeYear year) {
        printYearHeader(year);
        for (Day day : year.getDays()) {
            log.info(">>>>> Day {} <<<<<", Util.capitalize(day.dayName()));
            day.desc().ifPresent((desc) -> {
                log.info("{}", desc);
            });
            log.info("-------------------");
            day.run();
            log.info("-------------------");
        }
    }

    public void printYearHeader(ChallengeYear year) {
        log.info("-------------------");
        log.info("|       {}       |", year.getName());
        log.info("-------------------");
    }

    public abstract class ChallengeYear {
        public abstract List<Day> getDays();

        public String getName() {
            return this.getClass().getSimpleName();
        }
    }

    public class Y23 extends ChallengeYear {
        public List<Day> getDays() {
            return Arrays.asList(
                // new wrnkt.aoc.y23.One(),
                // new wrnkt.aoc.y23.Two(),
                new wrnkt.aoc.y23.Five()
            );
        }
    }
}
