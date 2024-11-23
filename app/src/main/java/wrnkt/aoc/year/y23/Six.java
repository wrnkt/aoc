package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import wrnkt.aoc.util.Day;

public class Six implements Day {

    private List<Race> readRaces(BufferedReader reader) {
        List<Race> races = new ArrayList<>();

        try {
            String timeReading = reader.readLine();
            String distanceReading = reader.readLine();

            String[] timeReadings = timeReading.split("\\s+");
            String[] distanceReadings = distanceReading.split("\\s+");

            for (int i = 1; i < timeReadings.length; ++i) {
                try {
                    races.add(
                        new Race(
                            Integer.parseInt(timeReadings[i]), 
                            Integer.parseInt(distanceReadings[i])
                        )
                    );
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to read from file: " + e);
        }
        return races;
    }

    public void partOne(List<Race> races) {
        List<Integer> waysToWinPerRace = new ArrayList<>();

        for (var race : races) {
            int waysToWin = waysToWin(race);
            waysToWinPerRace.add(waysToWin);
            log.info("{} -> {} ways to win", race.toString(), waysToWin);
        }

        var answerOpt = races.stream()
                        .map(r -> waysToWin(r))
                        .reduce((a, b) -> a * b);

        answerOpt.ifPresent((answer) -> {
            log.info("Part 1 answer: {}", answer);
        });
    }

    public void partTwo(List<Race> races) {

    }

    public int waysToWin(Race race) {
        int wins = 0;

        for (int holdTime = 0; holdTime < race.allowedTime(); holdTime++) {
            int distanceTraveled = Boat.travelDistance(holdTime, race.allowedTime());
            if (distanceTraveled > race.bestRecordedDistance()) wins++;
        }

        return wins;
    }

    @Override
    public void solution(BufferedReader reader) {
        List<Race> races = readRaces(reader);
        partOne(races);
    }

}

class Boat {
    public static int travelDistance(int buttonHoldMs, int maxTime) {
        int travelTime = maxTime - buttonHoldMs;
        int speed = buttonHoldMs;
        return travelTime * speed;
    }
}

record Race(int allowedTime, int bestRecordedDistance) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Race[");
        sb.append("allowedTime=" + allowedTime);
        sb.append(", ");
        sb.append("bestRecordedDistance=" + bestRecordedDistance);
        sb.append("]");
        return sb.toString();
    }
}
