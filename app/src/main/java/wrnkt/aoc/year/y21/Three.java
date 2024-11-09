package wrnkt.aoc.year.y21;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FLBitSet {
  private boolean[] bits;

  public FLBitSet(int l) {
    bits = new boolean[l];
  }

  public FLBitSet clone() {
    FLBitSet clone = new FLBitSet(this.length());
    clone.setArr(Arrays.copyOf(this.bits, this.bits.length));
    return clone;
  }

  public void set(int idx, boolean val) {
    bits[idx] = val;
  }

  public boolean[] getArr() {
    return bits;
  }

  public void setArr(boolean[] arr) {
    this.bits = arr;
  }

  public void flip(int fromIdx, int toIdx) {
    for (int i = fromIdx; i < toIdx; ++i) {
      this.bits[i] = !this.bits[i];
    }
  }

  public boolean get(int idx) {
    return bits[idx];
  }

  public int length() {
    return bits.length;
  }
}

class Pair<A, B> {
  A first;
  B second;

  public Pair(A first, B second) {
    this.first = first;
    this.second = second;
  }
}

public class Three {

  public static String toBinaryString(FLBitSet bitSet) {
    if (bitSet == null) {
      return null;
    }
    return IntStream.range(0, bitSet.length())
        .mapToObj(b -> String.valueOf(bitSet.get(b) ? 1 : 0))
        .collect(Collectors.joining());
  }

  public FLBitSet stringToBits(final String s) {
    FLBitSet bits = new FLBitSet(s.length());

    for (int i = 0; i < s.length(); ++i) {
      char bit = s.charAt(i);
      if (bit == '1') {
        bits.set(i, true);
      } else if (bit == '0') {
        bits.set(i, false);
      } else {
        System.err.println(String.format("Invalid character in bit set: %c", bit));
      }
    }
    return bits;
  }

  public static Pair<FLBitSet, FLBitSet> readInFile(String fName) {
    Path p = Paths.get(fName);

    FLBitSet gamma = null;
    FLBitSet epsilon = null;
    int lineCount = 0;
    Map<Integer, Integer> countMap = new HashMap<>();

    try {
      BufferedReader r = Files.newBufferedReader(p, StandardCharsets.UTF_8);
      String line;
      while ((line = r.readLine()) != null) {
        lineCount++;
        for (int i = 0; i < line.length(); ++i) {
          if (line.charAt(i) == '1') {
            countMap.put(i, countMap.getOrDefault(i, 0) + 1);
          } else {
            countMap.put(i, countMap.getOrDefault(i, 0));
          }
        }
      }

      gamma = new FLBitSet(countMap.size());

      for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
        System.out.println(String.format("key: %d | value: %d", entry.getKey(), entry.getValue()));
        if (entry.getValue() > (lineCount / 2)) {
          gamma.set(entry.getKey(), true);
        } else {
          gamma.set(entry.getKey(), false);
        }
      }
      epsilon = (FLBitSet) gamma.clone();
      epsilon.flip(0, 12);

    } catch (IOException e) {
      System.err.println(String.format("Could not read file %s", fName));
      System.err.println(e.getMessage());
    }
    return new Pair<FLBitSet, FLBitSet>(gamma, epsilon);
  }

  public static int toInt(FLBitSet bs) {
    int value = 0;
    for (int bit = 0; bit < bs.length(); ++bit) {
      if (bs.get(bit)) {
        value |= (1 << bit);
      }
    }
    return value;
  }

  public static int powerConsumption(FLBitSet gamma, FLBitSet epsilon) {
    return toInt(gamma) * toInt(epsilon);
  }

  public static void partOne() {
    String fName = "../input/three.txt";
    Pair<FLBitSet, FLBitSet> outputs = readInFile(fName);

    FLBitSet gamma = outputs.first;
    FLBitSet epsilon = outputs.second;

    int powerConsumption = powerConsumption(gamma, epsilon);
    System.out.println(String.format("power consumption = %d", powerConsumption)); // 2648450
  }

  public static void main(String... args) {
    partOne();
  }
}
