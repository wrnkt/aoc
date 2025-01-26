package wrnkt.aoc.year.y24;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import aoc.framework.Day;
import aoc.framework.util.Pair;

public class Six extends Day {

    @Override
    public void solution(BufferedReader reader) {
        partOne(reader);
        // print(data);
        // print(String.format("First comes UP %d", UP));
        // print(String.format("Then comes RIGHT %d", RIGHT));
        // print(String.format("Then comes DOWN %d", DOWN));
        // print(String.format("Then comes LEFT %d", LEFT));
        // print(String.format("Then comes UP %d", (LEFT<<1)));
    }

    public void partOne(BufferedReader reader) {
        print("Part One: ");

        List<List<Character>> data = readData(reader);
        List<List<Integer>> freqTable = calculateVisits(data);

        printFreqTable(freqTable);
    }

    final byte UP    =  0b00;
    final byte RIGHT =  0b01;
    final byte DOWN  =  0b10;
    final byte LEFT  =  0b11;

    private byte changeDirection(byte direction) {
        byte newDirection = direction++;
        if (newDirection > 3) return 0;
        return newDirection;
    }

    private List<List<Integer>> getZeroedFreqTable(int rows, int cols) {
        List<List<Integer>> freqTable = new ArrayList<>(rows);
        for (int y = 0; y < rows; y++) {
            List<Integer> row = new ArrayList<>(cols);
            for (int x = 0; x < cols; x++) {
                row.add(0);
            }
            freqTable.add(row);
        }
        return freqTable;
    }

    private boolean isObstacle(List<List<Character>> area, Coord c) {
        return (isInArea(area, c) && inspectCoord(area, c).equals('#'));
    }

    private void incFreqTable(List<List<Integer>> area, Coord c) {
        Integer count = area.get(c.y).get(c.x);
        count++;
    }

    private void printFreqTable(List<List<Integer>> freqTable) {
        print("Frequency Table: ");
        StringBuilder sb = new StringBuilder();
        for (List<Integer> row : freqTable) {
            for (Integer freq : row) {
                sb.append(String.format("%d", freq));
                sb.append(" ");
            }
            sb.append("\n");
        }
        print(sb.toString());
    }

    /**
     *  Returns a {@link Pair} of the next {@link Coord} and {@link Byte} direction of the move.
     *  An empty {@link Optional} represents the next move's coordinates being outside the bounds of the area.
     */
    private Optional<Pair<Coord, Byte>> execMove(List<List<Character>> area, Coord current, byte direction) {
        Coord possibleMove;
        switch (direction) {
            case UP:
                possibleMove = new Coord(current.x, current.y-1);
                break;
            case DOWN:
                possibleMove = new Coord(current.x, current.y+1);
                break;
            case RIGHT:
                possibleMove = new Coord(current.x+1, current.y);
                break;
            case LEFT:
                possibleMove = new Coord(current.x-1, current.y);
                break;
            default:
                return Optional.empty();
        }
        if (!isInArea(area, possibleMove)) {
            return Optional.empty();
        }
        if (isObstacle(area, possibleMove)) {
            return Optional.of(new Pair<Coord,Byte>(current, changeDirection(direction)));
        }
        return Optional.of(new Pair<Coord,Byte>(possibleMove, direction));
    }

    private List<List<Integer>> calculateVisits(List<List<Character>> area) {
        List<List<Integer>> freqTable = getZeroedFreqTable(rowCount(area), colCount(area));

        Optional<Coord> guard = findGuard(area);
        Coord guardCoord = guard.get();
        // print("guardCoord = " + guardCoord.toString());

        byte direction = UP;
        Coord newCoord = guardCoord;
        Optional<Pair<Coord, Byte>> newMovement;

        do {
            incFreqTable(freqTable, newCoord);
            newMovement = execMove(area, newCoord, direction);
            newCoord = newMovement.get().getFirst();
            direction = newMovement.get().getSecond();
        } while (newMovement.isPresent());
        return freqTable;
    }

    private boolean isInArea(List<List<Character>> area, Coord c) {
        if (
            (c.y < rowCount(area) && c.y > 0) 
            &&
            (c.x < colCount(area) && c.x > 0)
        ) return true;

        return false;
    }

    private Character inspectCoord(List<List<Character>> area, Coord c) {
        return area.get(c.y).get(c.x);
    }

    private <T> int rowCount(List<List<T>> data) {
        return data.size();
    }

    private <T> int colCount(List<List<T>> data) {
        List<Integer> rowLengths = new ArrayList<>();
        for (List<?> row : data) {
            rowLengths.add(row.size());
        }
        boolean sameLength = rowLengths.stream()
                            .allMatch(l -> l.equals(rowLengths.get(0)));
        assert sameLength == true;
        return rowLengths.get(0);
    }

    private Optional<Coord> findGuard(List<List<Character>> data) {
        for (int y = 0; y < rowCount(data); y++) {
            for (int x = 0; x < colCount(data); x++) {
                if (data.get(y).get(x).equals('^')) {
                    return Optional.of(new Coord(x, y));
                }
            }
        }
        return Optional.empty();
    }

    private void print(List<List<Character>> data) {
        StringBuilder sb = new StringBuilder();
        for (List<Character> row : data) {
            for (Character c : row) {
                sb.append(c);
            }
            sb.append("\n");
        }
        print(sb.toString());
    }

    private List<List<Character>> readData(BufferedReader reader) {
        List<List<Character>> data = new ArrayList<>();
        String line;
        try {
            List<Character> row = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                data.add(line.chars().mapToObj(c -> (char) c).toList());
            }
        } catch (IOException e) {
            log.error("Failed to read data", e);
        }
        return data;
    }

    record Coord(int x, int y) {
        @Override
        public String toString() {
            return String.format("Coord[x=%d, y=%d]", this.x, this.y);
        }
    }

}
