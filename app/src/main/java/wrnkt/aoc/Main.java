package wrnkt.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        AutoChallengeRunner.setup()
        .autoConfig()
        .build()
        .runAll();
    }
}
