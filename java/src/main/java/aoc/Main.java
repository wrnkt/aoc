package aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aoc.framework.AutoChallengeRunner;

public class Main {
    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        AutoChallengeRunner.setup()
            .autoConfig()
            .build()
            .runAll();
    }
}
