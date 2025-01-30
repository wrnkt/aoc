package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import aoc.framework.Day;
import wrnkt.aoc.year.y15.Six.Instruction.ACTION;

public class Six extends Day {

    @Override
    public void solution(BufferedReader reader) {
        List<Instruction> instructions = readData(reader);
        print(String.format("instructions read: %d", instructions.size()));
        partOne(instructions);
    }

    public void partOne(List<Instruction> instructions) {
        Lights lights = new Lights();
        instructions.stream()
                    .forEachOrdered(instruction -> lights.execute(instruction));
        long lightsOnCount = lights.lightsOn();
        print(String.format("Lights on: %d", lightsOnCount));
    }

    public List<Instruction> readData(BufferedReader reader) {
        return reader.lines()
                    .map(line -> new Instruction(line))
                    .collect(Collectors.toList());
    }

    class Lights {

        boolean[][] grid = new boolean[1000][1000];
        List<List<Boolean>> grid2 = new ArrayList<>();

        public Lights() {
            init();
            for (int i = 0; i < 1000; i++) {
                List<Boolean> row = new ArrayList<>();
                grid2.add(
                    Stream.generate(() -> false).limit(1000).collect(Collectors.toList())
                );
            }
        }

        public void execute2(Instruction inst) {
            for (int i = inst.a.x; i <= inst.b.x; i++) {
                for (int j = inst.a.y; j <= inst.b.y; j++) {
                    Boolean light = grid2.get(i).get(j);
                    switch (inst.action) {
                        case ON:
                            light = Boolean.TRUE;
                            break;
                        case OFF:
                            light = Boolean.FALSE;
                            break;
                        case TOGGLE:
                            light = !light;
                            break;
                    }
                }
            }
        }

        public void execute(Instruction inst) {
            long lightsChanged = 0;
            for (int i = inst.a.x; i <= inst.b.x; i++) {
                for (int j = inst.a.y; j <= inst.b.y; j++) {
                    boolean currentLightState = grid[i][j];

                    if (inst.action == ACTION.ON)
                        grid[i][j] = true;
                    else if (inst.action == ACTION.OFF)
                        grid[i][j] = false;
                    else if (inst.action == ACTION.TOGGLE)
                        grid[i][j] = !grid[i][j];
                    else 
                        throw new IllegalArgumentException("Unknown instruction type");

                    if (currentLightState != grid[i][j])
                        lightsChanged++;
                }
            }
            print("Instruction: " + inst.toString());
            print(String.format("lightsChanged: %d", lightsChanged));
        }

        public void init() {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    grid[i][j] = false;
                }
            }
        }

        public long lightsOn() {
            long count = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if(grid[i][j]) count++;
                }
            }
            return count;
        }

    }

    class Instruction {

        public ACTION action;
        public Coordinate a, b;
        
        public Instruction(String s) {
            setFrom(s);
        }

        private static final Pattern INPUT_PATTERN = 
            Pattern.compile("(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)");

        private void setFrom(String s) {
            Matcher m = INPUT_PATTERN.matcher(s);
            if (!m.matches()) {
                throw new IllegalArgumentException("Invalid instruction string provided: " + s);
            }
            this.action = ACTION.parse(m.group(1));
            this.a = new Coordinate(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
            this.b = new Coordinate(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
        }

        @Override
        public String toString() {
            return new StringBuilder()
                .append(action)
                .append(": ")
                .append(String.format("(%d, %d) -> (%d, %d)", a.x, a.y, b.x, b.y))
                .toString();
        }

        enum ACTION {
            TOGGLE, ON, OFF;

            public static ACTION parse(String s) {
                switch (s) {
                    case "turn on":
                        return ACTION.ON;
                    case "turn off":
                        return ACTION.OFF;
                    case "toggle":
                        return ACTION.TOGGLE;
                    default:
                        throw new RuntimeException("Unknown action");
                }
            }
        }
    }

    record Coordinate(int x, int y) {}
}
