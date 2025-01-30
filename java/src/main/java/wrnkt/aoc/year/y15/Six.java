package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.framework.Day;

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
    }

    public List<Instruction> readData(BufferedReader reader) {
        return reader.lines()
                    .map(line -> new Instruction(line))
                    .collect(Collectors.toList());
    }

    class Lights {

        boolean[][] grid = new boolean[1000][1000];

        public Lights() {
            init();
        }

        public void execute(Instruction inst) {
            for (int i = inst.a.x; i < inst.b.x; i++) {
                for (int j = inst.a.y; j < inst.b.y; j++) {
                    switch (inst.action) {
                        case ON:
                            grid[i][j] = true;
                        case OFF:
                            grid[i][j] = false;
                        case TOGGLE:
                            grid[i][j] = !grid[i][j];
                    }
                }
            }
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

        private static final Pattern INPUT_PATTERN = Pattern.compile("(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)");

        private void setFrom(String s) {
            Matcher m = INPUT_PATTERN.matcher(s);
            print(m.group(1));
            print(m.group(2));
            print(m.group(3));
            print(m.group(4));
            print(m.group(5));
            this.action = ACTION.parse(m.group(1));
            this.a = new Coordinate(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
            this.b = new Coordinate(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
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
