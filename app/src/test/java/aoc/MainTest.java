package aoc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void sanity() {
        Main m = new Main();
        assertNotNull(m, "Main should be instantiable.");
    }
}
