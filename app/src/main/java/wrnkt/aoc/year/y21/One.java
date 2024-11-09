package wrnkt.aoc.year.y21;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class One {

  public static ArrayList<Integer> readInValues(String fName) {
    ArrayList<Integer> readings = new ArrayList<>();
    Path p = Paths.get(fName);

    try {
      BufferedReader r = Files.newBufferedReader(p, StandardCharsets.UTF_8);
      String line;
      while ((line = r.readLine()) != null) {
        readings.add(Integer.parseInt(line));
      }

    } catch (IOException e) {
      System.err.println(String.format("Could not read file %s", fName));
      System.err.println(e.getMessage());
    }
    return readings;
  }

  public static int countLargerThanPrevious(ArrayList<Integer> list) {
    int count = 0;
    Integer prev = null;
    for (Integer i : list) {
      if (prev != null && i > prev)
        count++;
      prev = i;
    }
    return count;
  }

  public static int countSlidingWindowGrowth(ArrayList<Integer> list) {
    int count = 0;
    Deque<Integer> slidingWindow = new ArrayDeque<>();
    Integer currentSum = null;
    Integer previousSum = null;

    for (int i = 0; i < list.size(); ++i) {

      slidingWindow.addLast(list.get(i));

      if (slidingWindow.size() > 3) {
        slidingWindow.removeFirst();
      }

      if (slidingWindow.size() == 3) {
        currentSum = slidingWindow.stream().reduce(0, Integer::sum);
        if (previousSum != null) {
          if (currentSum > previousSum) {
            count++;
          }
        }
        previousSum = currentSum;
      }
    }
    return count;
  }

  public static void partOne() {
    String fName = "../input/one.txt";
    ArrayList<Integer> readings = readInValues(fName);
    System.out.println(
        String.format("There are %d measurements larger than the previous measurement.",
            countLargerThanPrevious(readings)));
  }

  public static void partTwo() {
    String fName = "../input/one.txt";
    ArrayList<Integer> readings = readInValues(fName);
    System.out.println(
        String.format("There are %s sums larger than the previous sums.", countSlidingWindowGrowth(readings)));
  }

  public static void main(String... args) {
    partOne();
    partTwo();
  }
}
