package wrnkt.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        // ChallengeRunner cr = new ChallengeRunner();
        // cr.runAll();
        var r = AutoChallengeRunner.setup()
                .addPuzzle(2023, 1)
                // .addPuzzle(2023, 2)
                // .addPuzzle(2023, 3)
                .build();
        r.runAll();
    }
}
