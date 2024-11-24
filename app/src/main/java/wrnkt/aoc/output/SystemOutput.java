package wrnkt.aoc.output;

public class SystemOutput extends DayOutput {
    public void submit(String s) {
        System.out.println(s);
    }

    public boolean isReady() { return true;}
}
