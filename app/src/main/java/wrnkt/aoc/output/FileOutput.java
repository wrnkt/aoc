package wrnkt.aoc.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;

public class FileOutput implements DayOutput {
    public static final Logger log = LoggerFactory.getLogger(FileOutput.class);

    private Path base = Paths.get(getClass().getResource("/").getPath());
    private Path outputFolder;

    private PrintWriter out;
    private boolean isOutRegistered = false;

    public FileOutput() {
        outputFolder = Paths.get(base.toAbsolutePath() + "/output/");
    }

    public void registerDay(Day day) {
        try {
            Files.createDirectory(outputFolder);
            FileWriter fw = new FileWriter(outputFolder + getDayFileName(day), true);
            BufferedWriter bw  = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            isOutRegistered = true;
            day.setOutput(this);
            // register with Day
            // day.registerOutput(out);
        } catch (Exception e) {
            log.error("Failed to properly register output with day.");
            System.out.println("failed to create outputFolder");
        }
    }

    private String getDayFileName(Day day) throws Exception {
        var dayName = day.getSpelledDay().orElseThrow();
        var year = day.getYear().orElseThrow();
        return String.format("%d_%s.out", year, dayName);
    }

    public void submit(String s) {
        out.println(s);
    }
}
