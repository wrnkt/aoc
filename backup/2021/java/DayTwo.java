import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

enum Direction {
  FORWARD, DOWN, UP;

  public static Map<String, Direction> map = new HashMap<>();

  static {
    map.put("forward", Direction.FORWARD);
    map.put("down", Direction.DOWN);
    map.put("up", Direction.UP);
  }

  public static Direction get(String s) {
    switch (s) {
      case "forward":
        return Direction.FORWARD;
      case "down":
        return Direction.DOWN;
      case "up":
        return Direction.UP;
      default:
        return null;
    }
  }

  @Override
  public String toString() {
    Optional<String> dirName = map
        .entrySet()
        .stream()
        .filter(entry -> this.equals(entry.getValue()))
        .map(Map.Entry::getKey)
        .findFirst();
    return dirName.isPresent() ? dirName.get().toUpperCase() : "NULL";
  }
}

class Position {
  Direction xDir = null;
  int xMag = 0;
  Direction yDir = null;
  int yMag = 0;
  int aim = 0;

  void add(Direction direction, int magnitude) {
    if (direction == Direction.DOWN) {
      aim += magnitude;
    } else if (direction == Direction.UP) {
      aim -= magnitude;
    } else if (direction == Direction.FORWARD) {
      xDir = Direction.FORWARD;
      xMag += magnitude;
      yMag += (aim * magnitude);
    }
  }

  public static Position getPosition(ArrayList<Step> steps) {
    Position pos = new Position();
    for (Step step : steps)
      pos.add(step.direction, step.magnitude);
    return pos;
  }
}

class Step {
  Direction direction;
  int magnitude;

  public Step(Direction d, int m) {
    this.direction = d;
    this.magnitude = m;
  }

  public static Step parse(String str) {
    Direction d = Direction.get(str.substring(0, str.indexOf(" ")));
    int m = Integer.parseInt(str.substring(str.indexOf(" ") + 1));
    return (d != null) ? new Step(d, m) : null;
  }
}

class Display {
  static void show(Step s) {
    System.out.println(String.format("%s %d", Direction.toString(s.direction), s.magnitude));
  }

  static void show(Position p) {
    System.out.println(String.format("%s %d", Direction.toString(p.xDir), p.xMag));
    System.out.println(String.format("%s %d", Direction.toString(p.yDir), p.yMag));
  }

}

public class DayTwo {

  public static ArrayList<Step> readInFile(String fName) {
    ArrayList<Step> course = new ArrayList<>();
    Path p = Paths.get(fName);

    try {
      BufferedReader r = Files.newBufferedReader(p, StandardCharsets.UTF_8);
      String line;
      while ((line = r.readLine()) != null) {
        Step step = Step.parse(line);
        System.out.println(String.format("dir: %s | mag: %d", Direction.toString(step.direction), step.magnitude));
        course.add(step);
      }

    } catch (IOException e) {
      System.err.println(String.format("Could not read file %s", fName));
      System.err.println(e.getMessage());
    }
    return course;
  }

  // public static Position getPosition(ArrayList<Step> course) {
  // Position pos = new Position();
  // for (Step step : course) {
  // Display.show(step);
  // pos.add(step.direction, step.magnitude);
  // System.out.println("Updated pos: ");
  // Display.show(pos);
  // System.out.println("--------------");
  // }
  // return pos;
  // }

  public static void partOne(String fName) {
    ArrayList<Step> course = readInFile(fName);
    Position position = getPosition(course);
    int horiz = position.xMag;
    int vert = position.yMag;
    System.out.println(String.format("The final answer is %d", (horiz * vert)));
  }

  public static void partTwo(String fName) {

  }

  public static void main(String... args) {
    String fName = "../input/two.txt";
    partOne(fName);
  }
}
