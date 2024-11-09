package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import wrnkt.aoc.util.Day;

enum Color {
    BLUE, RED, GREEN
}

record GameInfo(int id, ArrayList<Map<Color, Integer>> handfuls) {
}

public class Two implements Day {

    public Optional<String> desc() {
        return Optional.of("Cant remember desc");
    }

    public static final Pattern idPattern = Pattern.compile("Game (?<id>\\d+)");
    public static final Pattern numPattern = Pattern.compile("(\\d+)");

    public static void printGameInfo(GameInfo game) {
        System.out.println("ID: " + game.id());
        int count = 1;
        for (Map<Color, Integer> handful : game.handfuls()) {
            System.out.println(String.format("Handful %d: ", count));
            System.out.println();
            for (Map.Entry<Color, Integer> colorCount : handful.entrySet()) {
                System.out.println(String.format("\t%s: %d", colorCount.getKey(), colorCount.getValue()));
            }
            count++;
        }
    }

    private static ArrayList<Map<Color, Integer>> processGameColors(final String s) {
        ArrayList<Map<Color, Integer>> handfullList = new ArrayList<>();

        String handfullsString = s.split(":")[1].strip();
        String[] handfullsArr = handfullsString.split(";", 0);

        for (String handfull : handfullsArr) {
            String[] colorArr = handfull.split(",", 0);
            Map<Color, Integer> handfulMap = new HashMap<>();
            for (String colorCount : colorArr) {
                int num = 0;
                Color color = null;

                Matcher m = numPattern.matcher(colorCount);

                if (!m.find()) {
                    System.out.println("[ERROR]: big error panic");
                }

                num = Integer.parseInt(m.group(0));

                if (colorCount.contains("blue")) {
                    color = Color.BLUE;
                } else if (colorCount.contains("red")) {
                    color = Color.RED;
                } else if (colorCount.contains("green")) {
                    color = Color.GREEN;
                }

                handfulMap.put(color, Integer.valueOf(num));
            }
            handfullList.add(handfulMap);
        }
        return handfullList;

    }

    private static int processGameId(final String s) {

        Matcher idMatcher = idPattern.matcher(s);
        String strId;
        Integer id = 0;

        if (idMatcher.find()) {
            strId = idMatcher.group("id");
            id = Integer.parseInt(strId);
        }

        return id;
    }

    private static GameInfo processGame(final String s) {
        GameInfo g = new GameInfo(processGameId(s), processGameColors(s));
        return g;
    }

    private static ArrayList<GameInfo> loadGamesFromFile(Path path) {
        ArrayList<GameInfo> games = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line;
            while ((line = reader.readLine()) != null) {
                games.add(processGame(line));
            }
        } catch (IOException e) {
            System.out.println(String.format("[ERROR]: could not read file %d", path.toString()));
        }
        return games;
    }

    private static ArrayList<GameInfo> loadGamesFromReader(BufferedReader reader) {
        ArrayList<GameInfo> games = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                games.add(processGame(line));
            }
        } catch (IOException e) {
            System.out.println(String.format("[ERROR]: Problem reading"));
        }
        return games;
    }

    private static boolean validGame(Map<Color, Integer> bagContents, GameInfo game) {
        for (Map<Color, Integer> handful : game.handfuls()) {
            for (Map.Entry<Color, Integer> colorCount : handful.entrySet()) {
                Color color = colorCount.getKey();
                Integer count = colorCount.getValue();
                if (count > bagContents.get(color))
                    return false;
            }
        }
        return true;
    }

    private static Map<Color, Integer> minimumSet(GameInfo game) {
        Map<Color, Integer> minimumSet = new HashMap<>();
        minimumSet.put(Color.RED, 0);
        minimumSet.put(Color.GREEN, 0);
        minimumSet.put(Color.BLUE, 0);
        for (Map<Color, Integer> handful : game.handfuls()) {
            for (Map.Entry<Color, Integer> colorCount : handful.entrySet()) {
                Color color = colorCount.getKey();
                Integer count = colorCount.getValue();
                if (minimumSet.get(color) < count) {
                    minimumSet.put(color, count);
                }
            }
        }
        return minimumSet;
    }

    private static Integer powerSet(Map<Color, Integer> set) {
        int power = 1;
        for (Map.Entry<Color, Integer> colorCount : set.entrySet()) {
            Integer count = colorCount.getValue();
            power *= count;
        }
        return power;
    }

    private static void partOne(ArrayList<GameInfo> games) {
        Map<Color, Integer> bagContents = new HashMap<>();
        bagContents.put(Color.RED, 12);
        bagContents.put(Color.GREEN, 13);
        bagContents.put(Color.BLUE, 14);

        Stream<GameInfo> validGames = games.stream().filter((g) -> validGame(bagContents, g));
        Integer sumValidIds = validGames.mapToInt((g) -> g.id()).sum();

        System.out.println(String.format("Sum of valid game IDs: %d", sumValidIds));
    }

    private static void partTwo(ArrayList<GameInfo> games) {
        Integer sumOfPowerSet = games.stream()
                .map((g) -> minimumSet(g))
                .mapToInt((s) -> powerSet(s))
                .sum();
        System.out.println(String.format("Sum of powers: %d", sumOfPowerSet));
    }

    public void run() {
        try {
            BufferedReader reader = getInputReader();
            ArrayList<GameInfo> games = loadGamesFromReader(reader);

            partOne(games);
            partTwo(games);
        } catch (Exception e) {
            System.out.println("[ERROR]: could not get reader for input.");
        }

    }

}
