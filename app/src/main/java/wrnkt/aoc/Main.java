package wrnkt.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        Config config = new Config();
        var r = AutoChallengeRunner.setup()
                .autoConfig()
                // .addPuzzleRange(2023, 1, 6)
                // .addPuzzle(2023, 7)
                .addPuzzle(2023, 8)
                // .addPuzzle(2023, 9)
                // .addPuzzle(2023, 9)
                .build();

        r.runAll();
    }
}
