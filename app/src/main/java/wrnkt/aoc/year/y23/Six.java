package wrnkt.aoc.year.y23;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                            Long.parseUnsignedLong(timeReadings[i]), 
                            Long.parseUnsignedLong(distanceReadings[i])
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
            List<Boolean> holdTimeStats = holdTimeStats(race);
            for (int i = 0; i < holdTimeStats.size(); i++) {
                log.info("Race results:");
                log.info("holdTIme {} ms{ }", i, (holdTimeStats.get(i) ? "wWON" : "" ));
            }
        }

        var answerOpt = races.stream()
                        .map(r -> waysToWin(r))
                        .reduce((a, b) -> a * b);

        answerOpt.ifPresent((answer) -> {
            log.info("Part 1: {}", answer);
        });
    }

    public void partTwo(List<Race> races) {
        Object time = races.stream()
                .map(r ->  r.allowedTime())
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        assert (time instanceof StringBuilder);
        time = Long.parseUnsignedLong(time.toString());

        Object distance = races.stream()
                .map(r -> r.bestRecordedDistance())
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        assert (distance instanceof StringBuilder);
        distance = Long.parseUnsignedLong(distance.toString());

        var newRace = new Race((long) time, (long) distance);

        log.info("Part 2: {}");
    }

    public int waysToWin(Race race) {
        int wins = 0;

        for (int holdTime = 0; holdTime < race.allowedTime(); holdTime++) {
            long distanceTraveled = Boat.travelDistance(holdTime, race.allowedTime());
            if (distanceTraveled > race.bestRecordedDistance()) wins++;
        }

        return wins;
    }

    public List<Boolean> holdTimeStats(Race race) {
        List<Boolean> holdTimeStats = new ArrayList<>();

        var didWin = false;
        for (int holdTime = 0; holdTime < race.allowedTime(); holdTime++) {
            long distanceTraveled = Boat.travelDistance(holdTime, race.allowedTime());
            if (distanceTraveled > race.bestRecordedDistance()) didWin = true;

            holdTimeStats.add(holdTime, didWin);

        }
        assert (holdTimeStats.size() == race.allowedTime());
        return holdTimeStats;
    }

    @Override
    public void solution(BufferedReader reader) {
        List<Race> races = readRaces(reader);
        partOne(races);
        partTwo(races);
    }

}

class Boat {
    public static long travelDistance(long buttonHoldMs, long maxTime) {
        long travelTime = maxTime - buttonHoldMs;
        long speed = buttonHoldMs;
        return travelTime * speed;
    }
}

record Race(long allowedTime, long bestRecordedDistance) {

    public Race(String time, String distance) {
        this(Long.parseUnsignedLong(time), Long.parseUnsignedLong(distance));
    }

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
