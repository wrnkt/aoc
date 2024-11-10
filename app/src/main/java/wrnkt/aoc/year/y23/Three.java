package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import wrnkt.aoc.util.Day;

class Point {
    private int X;
    private int Y;

    public Point() {
        this(0, 0);
    }

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int X) {
        this.X = X;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public boolean isAdjacent(Point other) {
        int lowX = this.X - 1;
        int highX = this.X + 1;
        int lowY = this.Y - 1;
        int highY = this.Y + 1;
        if ((other.getX() >= lowX && other.getX() <= highX) &&
                (other.getY() >= lowY && other.getY() <= highY)) {
            return true;
        }
        return false;
    }
}

class Number {
    private int value;
    private StringBuilder literal = new StringBuilder();
    private ArrayList<Point> location = new ArrayList<>();
    private boolean isPartNumber = false;

    public void addDigit(char c, int col, int row) {
        literal.append(c);
        updateValue();
        location.add(new Point(col, row));
    }

    private void updateValue() {
        value = Integer.parseInt(literal.toString());
    }

    public int getValue() {
        return value;
    }

    public boolean isPartNumber() {
        return isPartNumber;
    }

    private boolean adjacentTo(Point point) {
        for (Point p : location) {
            if (point.isAdjacent(p))
                return true;
        }
        return false;
    }

    public void checkPartNumber(Point point) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Checking Number with points:\n"));
        for (Point p : this.location) {
            sb.append(String.format("\t(%d, %d)\n", p.getX(), p.getY()));
        }
        sb.append(String.format("Against Point: (%d, %d)\n", point.getX(), point.getY()));
        System.out.println(sb.toString());
        if (adjacentTo(point)) {
            System.out.println("ADJACENT");
            this.isPartNumber = true;
        }
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Value: %d\n", this.value));
        sb.append(String.format("Literal: %s\n", this.literal.toString()));
        sb.append(String.format("Points:\n", this.value));
        for (Point p : location) {
            sb.append(String.format("\t(%d, %d)\n", p.getX(), p.getY()));
        }
        sb.append(String.format("isPartNumber: %b\n", this.isPartNumber));
        System.out.println(sb.toString());
    }
}

public class Three implements Day {

    public static boolean isSymbol(Character c) {
        if (Character.isDigit(c) || c == '.') {
            return false;
        }
        return true;
    }

    public static ArrayList<Number> readInNumbers(Path path) {
        ArrayList<Number> numbers = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            boolean processingNumber = false;
            Number curNumber = null;

            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {

                for (int col = 0; col < line.length(); ++col) {

                    char c = line.charAt(col);

                    if (Character.isDigit(c)) {
                        if (!processingNumber) {
                            processingNumber = true;
                            curNumber = new Number();
                            numbers.add(curNumber);
                        }
                        curNumber.addDigit(c, col, row);

                    } else {
                        if (processingNumber) {
                            processingNumber = false;
                        }
                    }
                }
                ++row;
            }
        } catch (IOException e) {

        }
        return numbers;
    }

    public static void checkPartNumber(Path path, ArrayList<Number> numbers) {
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            int row = 0;
            String line;
            while ((line = reader.readLine()) != null) {

                for (int col = 0; col < line.length(); ++col) {
                    char c = line.charAt(col);

                    if (isSymbol(c)) {
                        Point point = new Point(col, row);
                        numbers.stream().forEach((n) -> n.checkPartNumber(point));
                    }
                }
                ++row;
            }
        } catch (IOException e) {

        }
    }

    public static ArrayList<Number> processFile(Path path) {
        ArrayList<Number> numbers = readInNumbers(path);
        checkPartNumber(path, numbers);
        return numbers;
    }

    @Override
    public void solution(BufferedReader br) {
        try {
            ArrayList<Number> nums = processFile(Path.of(""));

            Stream<Number> partNumbers = nums.stream().filter((n) -> n.isPartNumber());

            Integer sum = partNumbers.mapToInt((pn) -> pn.getValue()).sum();
            System.out.println(String.format("Sum: %d", sum));

        } catch (Exception e) {
            System.out.println("[ERROR]: Failed to load input data.");
        }
    }
}
