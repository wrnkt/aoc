package wrnkt.aoc.year.y16;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aoc.framework.Day;
import aoc.framework.util.Pair;

public class One extends Day {

    public final byte NORTH = 0b00;
    public final byte EAST = 0b01;
    public final byte SOUTH = 0b10;
    public final byte WEST = 0b11;

    public void solution(BufferedReader reader) {
        String s = readData(reader);
        List<String> data = new ArrayList<>(Arrays.asList(s.split(", ")));
        data.forEach(this::turnAndWalk);
        log.info("current location: ({}, {})", location.x, location.y);
        log.info("distance: {}", distance(location.x, location.y));
    }

    public String readData(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            log.error("Failed to read input");
        }
        return null;
    }

    public byte currentDirection = NORTH;
    public Coord location = new Coord(0, 0);

    public void turnAndWalk(String s) {
        log.info("processing: {}", s);
        try {
            char direction = s.substring(0, 1).charAt(0);
            Integer distance = Integer.parseInt(s.substring(1, 2));
            turnAndWalk(distance, (direction == 'R'));
        } catch (NumberFormatException e) {
            log.error("Failed to parse {}", s);
        }
        log.info("location: ({}, {})", location.x, location.y);
    }

    public void turnAndWalk(int distance, boolean right) {
        currentDirection = (right) ?
            (byte) right(currentDirection) :
            (byte) left(currentDirection);
        switch (currentDirection) {
            case NORTH:
                location.y += distance;
                break;
            case EAST:
                location.x += distance;
                break;
            case SOUTH:
                location.y -= distance;
                break;
            case WEST:
                location.x -= distance;
                break;
            default:
                throw new RuntimeException("Unknown direction");
        }
    }

    public double distance(int x, int y) {
        return Math.sqrt(x*x + y*y);
    }

    public int right(byte b) {
        return (b + 1) & 0b11;
    }

    public int left(byte b) {
        return (b - 1) & 0b11;
    }

    class Coord {
        public int x = 0;
        public int y = 0;
        public Coord() {}
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
}
