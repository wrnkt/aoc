package wrnkt.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        var r = AutoChallengeRunner.setup()
                .addPuzzle(2023, 1)
                // .addPuzzle(2023, 2)
                // .addPuzzle(2023, 3)
                // .addPuzzle(2023, 4)
                // .addPuzzle(2023, 5)
                // .addPuzzle(2023, 6)
                // .addPuzzle(2023, 7)
                // .addPuzzle(2023, 8)
                // .addPuzzle(2023, 9)
                // .addPuzzle(2023, 9)
                .build();

        r.runAll();
    }
}
