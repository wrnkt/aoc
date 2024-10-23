package wrnkt.aoc.y23;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wrnkt.aoc.util.Day;

public class Five implements Day {

    private List<Long> seeds = new ArrayList<>();

    enum ValueType {
        SEED, SOIL, FERT, WATER, LIGHT, TEMP, HUMIDITY, LOCATION;

        public static ValueType getOrdinal(int i) {
            if (i >= 0 && i < ValueType.values().length) {
                return ValueType.values()[i];
            }
            throw new RuntimeException();
        }
    }

    private List<ReferenceMap> seedToSoil = new ArrayList<>();
    private List<ReferenceMap> soilToFertilizer = new ArrayList<>();
    private List<ReferenceMap> fertilizerToWater = new ArrayList<>();
    private List<ReferenceMap> waterToLight = new ArrayList<>();
    private List<ReferenceMap> lightToTemp = new ArrayList<>();
    private List<ReferenceMap> tempToHumidity = new ArrayList<>();
    private List<ReferenceMap> humidityToLocation = new ArrayList<>();

    public List<List<ReferenceMap>> maps = Arrays.asList(
            seedToSoil,
            soilToFertilizer,
            fertilizerToWater,
            waterToLight,
            lightToTemp,
            tempToHumidity,
            humidityToLocation
        );

    public record ReferenceMap(long destStart, long sourceStart, long length) {
        public static ReferenceMap fromLine(String line) {
            String[] nums = line.split(" ");
            return new ReferenceMap(
                    Long.parseLong(nums[0]),
                    Long.parseLong(nums[1]),
                    Long.parseLong(nums[2])
            );
        }
    }

    public void readData(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("seeds:")) {
                String seedsStr = line;
                seedsStr = seedsStr.substring(7);
                Arrays.stream(seedsStr.split(" "))
                    .forEach((s) -> {
                        try {
                            seeds.add(Long.parseLong(s));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            throw new RuntimeException("Couldn't parse seeds.");
                        }
                    });
                System.out.println(String.format("[INFO]: Parsed %d seeds", seeds.size()));
            }
            if (line.startsWith("seed-to-soil map:")) {
                line = br.readLine();
                do {
                    seedToSoil.add(ReferenceMap.fromLine(line));
                    line = br.readLine();
                } while ((line != null) && !line.isBlank());
                System.out.println(String.format("[INFO]: seedToSoil map size: %d", seedToSoil.size()));
            }
            if (line.startsWith("soil-to-fertilizer map:")) {
                line = br.readLine();
                do {
                    soilToFertilizer.add(ReferenceMap.fromLine(line));
                    line = br.readLine();
                } while ((line != null) && !line.isBlank());
                System.out.println(String.format("[INFO]: soilToFertilizer map size: %d", seedToSoil.size()));
            }
            if (line.startsWith("fertilizer-to-water map:")) {
                line = br.readLine();
                do {
                    fertilizerToWater.add(ReferenceMap.fromLine(line));
                    line = br.readLine();
                } while ((line != null) && !line.isBlank());
                System.out.println(String.format("[INFO]: fertilizerToWater map size: %d", seedToSoil.size()));
            }
            if (line.startsWith("water-to-light map:")) {
                line = br.readLine();
                do {
                    waterToLight.add(ReferenceMap.fromLine(line));
                    line = br.readLine();
                } while ((line != null) && !line.isBlank());
                System.out.println(String.format("[INFO]: waterToLight map size: %d", seedToSoil.size()));
            }
            if (line.startsWith("light-to-temperature map:")) {
                line = br.readLine();
                do {
                    lightToTemp.add(ReferenceMap.fromLine(line));
                    line = br.readLine();
                } while ((line != null) && !line.isBlank());
                System.out.println(String.format("[INFO]: lightToTemp map size: %d", seedToSoil.size()));
            }
            if (line.startsWith("temperature-to-humidity map:")) {
                line = br.readLine();
                do {
                    tempToHumidity.add(ReferenceMap.fromLine(line));
                    line = br.readLine();
                } while ((line != null) && !line.isBlank());
                System.out.println(String.format("[INFO]: tempToHumidity map size: %d", seedToSoil.size()));
            }
            if (line.startsWith("humidity-to-location map:")) {
                line = br.readLine();
                do {
                    humidityToLocation.add(ReferenceMap.fromLine(line));
                    line = br.readLine();
                } while ((line != null) && !line.isBlank());
                System.out.println(String.format("[INFO]: humidityToLocation map size: %d", seedToSoil.size()));
            }
        }
    }

    public void sort() {
        for (List<ReferenceMap> map : maps) {
            var sortedMap = map.stream()
                            .sorted((a, b) -> (Long.compare(a.sourceStart, b.sourceStart)))
                            .toList();
            map.clear();
            map.addAll(sortedMap);
        }
    }

    public void printResults(Map<Long, HashMap<ValueType, Long>> data) {
        for (var seedEntry : data.entrySet()) {
            System.out.println(String.format("------------------"));
            System.out.println(String.format("Seed %d", seedEntry.getKey()));
            for (var entry : seedEntry.getValue().entrySet()) {
                System.out.println(String.format("    %s %d", entry.getKey(), entry.getValue()));
            }
        }
    }

    public void printLowestLocationNumber(Map<Long, HashMap<ValueType, Long>> data) {

        var lowest = data.values().stream()
                        .flatMap((map) -> map.entrySet().stream())
                        .filter((entry) -> entry.getKey() == ValueType.LOCATION)
                        .mapToLong((entry) -> entry.getValue())
                        .min();

        if (lowest.isEmpty()) {
            System.out.println("[ERROR]: could not find the lowest location number.");
            return;
        }
        System.out.println(String.format("Lowest location number: %d", lowest.getAsLong()));
    }

    public Map<Long, HashMap<ValueType, Long>> mapSeeds() {

        Map<Long, HashMap<ValueType, Long>> results = new HashMap<>();

        for (long seed : seeds) {
            results.put(seed, new HashMap<>());
            long categoryValue = seed;
            int valueType = 1;
            for (List<ReferenceMap> map : maps) {
                boolean wasMapped = false;
                for (ReferenceMap entry : map) {
                    long rangeStart = entry.sourceStart;
                    long rangeLength = entry.length;
                    long rangeEnd = rangeStart + rangeLength;
                    if (categoryValue >= rangeStart && categoryValue <= rangeEnd) {
                        if (wasMapped) {
                            System.out.println("Attempting to map twice.");
                        }
                        long offset = categoryValue - rangeStart;
                        assert(offset >= 0);
                        long mappedValue = entry.destStart + offset;
                        categoryValue = mappedValue;
                        wasMapped = true;
                        break;
                    }
                }
                if (!wasMapped) {
                    // System.out.println("[INFO]: Value not found in map.");
                    // System.out.println(String.format("[INFO]: Defaulting to original value: %d", originalValue));
                }
                results.get(seed).put(ValueType.getOrdinal(valueType), categoryValue);
                valueType++;
            }
        }
        return results;
    }

    public void run() {
        try {
            BufferedReader reader = getInputReader();
            readData(reader);
            sort();
            Map<Long, HashMap<ValueType, Long>> data = mapSeeds();
            printResults(data);
            printLowestLocationNumber(data);

        } catch (Exception e) {
            System.out.println("[ERROR]: could not get reader for input.");
            System.out.println(e.getMessage());
        }
    }
    
}

class SeedInfo {
    public long seed;
    public long soil;
    public SeedInfo() {}
}

