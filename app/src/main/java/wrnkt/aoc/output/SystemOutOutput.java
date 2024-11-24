package wrnkt.aoc.output;

import wrnkt.aoc.util.Day;

public class SystemOutOutput extends DayOutput {
    
    public SystemOutOutput(Day day) {
        registerDay(day);
    }

    public void submit(String s) {
        System.out.println(s);
    }

    public boolean isReady() { return true;}
}
