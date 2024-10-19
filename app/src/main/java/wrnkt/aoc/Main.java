package wrnkt.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.y23.Y23;

public class Main {
    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        log.info("Starting...");
        Y23 twentyTwentyThree = new Y23();
        twentyTwentyThree.run();
    }
}
