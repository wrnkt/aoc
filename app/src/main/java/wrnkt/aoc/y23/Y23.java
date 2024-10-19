package wrnkt.aoc.y23;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;

public class Y23 {
    public static final Logger log = LoggerFactory.getLogger(Y23.class);

    private final List<Day> days = new ArrayList<Day>();

    public Y23() {
        days.add(new One());
        days.add(new Two());
    }

    public void run() {
        notifyStarting();
        for(Day d : days) {
            log.info("Day {}", d.number());
            log.info("{}", d.desc());
            d.run();
            log.info("-------------------");
        }
    }

    public static void notifyStarting() {
        log.info(".------------------.");
        log.info("|       2023       |");
        log.info("\\__________________/");
    }
}
