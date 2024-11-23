package wrnkt.aoc.output;

import java.util.Map;

import wrnkt.aoc.util.Day;

public interface DayOutput {
    public void registerDay(Day day);
    public void submit(String s);
}
