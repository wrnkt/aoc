package wrnkt.aoc.year.y15;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import aoc.framework.Day;

public class Three extends Day {
    public static final char NORTH = '^';
    public static final char SOUTH = 'v';
    public static final char EAST  = '>';
    public static final char WEST  = '<';

    @Override
    public void solution(BufferedReader reader) {
        List<Character> moves = readData(reader);
        partOne(moves);
        partTwo(moves);
    }

    public void partTwo(List<Character> moves) {
        print("Part Two:");

        List<Coordinate> visitedLocations = new ArrayList<>();
        Coordinate currentLocation = new Coordinate(0, 0);

        visitedLocations.add(currentLocation);
        for (int i = 0; i < moves.size(); i = i + 2) {
            currentLocation = currentLocation.clone();
            currentLocation.move(moves.get(i));
            visitedLocations.add(currentLocation);
        }

        currentLocation = new Coordinate(0, 0);
        for (int i = 1; i < moves.size(); i = i + 2) {
            currentLocation = currentLocation.clone();
            currentLocation.move(moves.get(i));
            visitedLocations.add(currentLocation);
        }

        List<Coordinate> dedupedLocations = condense(visitedLocations);
        print(String.format("unique locations visited: %d", dedupedLocations.size()));

    }

    public void partOne(List<Character> moves) {
        print("Part One:");
        List<Coordinate> visitedLocations = new ArrayList<>();

        Coordinate currentLocation = new Coordinate(0, 0);
        visitedLocations.add(currentLocation);

        for (Character move : moves) {
            currentLocation = currentLocation.clone();
            currentLocation.move(move);
            visitedLocations.add(currentLocation);
        }
        print(String.format("moves recorded: %d", visitedLocations.size()));

        List<Coordinate> dedupedLocations = condense(visitedLocations);
        print(String.format("unique locations visited: %d", dedupedLocations.size()));
    }

    public List<Coordinate> condense(List<Coordinate> locations) {
        List<Coordinate> condensed = new ArrayList<>();

        for (Coordinate current : locations) {
            long count = locations.stream()
                            .filter(coord -> coord.sameCoord(current))
                            .count();
            current.visits = (int) count;
            if (!condensed.contains(current))
                condensed.add(current);
        }
        return condensed;
    }

    public List<Character> readData(BufferedReader reader) {
        Stream<Character> cStream = reader.lines()
                                .flatMap(line -> line.chars()
                                                    .mapToObj(c -> (char) c)
                                );
        return cStream.toList();
    }

    class Coordinate {
        int x;
        int y;
        int visits = 0;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinate clone() {
            return new Coordinate(this.x, this.y);
        }

        public void visit() {
            visits++;
        }

        public void move(Character c) {
            switch (c) {
                case NORTH: moveNorth();
                    break;
                case SOUTH: moveSouth();
                    break;
                case EAST:  moveEast();
                    break;
                case WEST:  moveWest();
                    break;
                default:
                    throw new RuntimeException("Unknown movement specified: " + c);
            }
        }

        public void moveNorth() { y++; }
        public void moveSouth() { y--; }
        public void moveEast()  { x++; }
        public void moveWest()  { x--; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Coordinate other = (Coordinate) obj;
            return sameCoord(other);
        }

        public boolean sameCoord(Coordinate other) {
            return (other.x == x) && (other.y == y);
        }

    }

}
