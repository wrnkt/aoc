package wrnkt.aoc.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wrnkt.aoc.util.Day;

public class FileOutput extends DayOutput {
    public static final Logger log = LoggerFactory.getLogger(FileOutput.class);

    public static final String DEFAULT_OUTPUT_DIR_NAME = "output/";

    private Path BASE_PATH = Paths.get(getClass().getResource("/").getPath());
    private Path outputFolder = Paths.get(BASE_PATH.toAbsolutePath() + DEFAULT_OUTPUT_DIR_NAME);

    private PrintWriter printWriter = null;

    public FileOutput() {
        this(DEFAULT_OUTPUT_DIR_NAME);
    }
    
    public FileOutput(String outputDirName) {
        outputFolder = Paths.get(BASE_PATH.toAbsolutePath() + outputDirName);
    }

    private void createOutputFolder() {
        if (Files.exists(outputFolder)) {
            return;
        }
        try {
            Files.createDirectory(outputFolder);
        } catch (IOException e) {
            log.error("{}", e);
        }
    }

    private Optional<PrintWriter> createDayOutputFile(Day day) {
        try {
            var fileName = getDayFileName(day);
            Path dayPath = outputFolder.resolve(fileName);
            Files.createDirectories(dayPath.getParent());
            FileWriter fw = new FileWriter(outputFolder.resolve(fileName).toFile());
            return Optional.of(new PrintWriter(new BufferedWriter(fw)));
        } catch (Exception e) {
            log.error("{}", e);
        }
        return Optional.empty();
    }

    public void registerDay(Day day) {
        createOutputFolder();
        var outputOpt = createDayOutputFile(day);
        outputOpt.ifPresentOrElse((printWriter) -> {
            printWriter = printWriter;
        }, () -> {
        });

        super.registerDay(day);
    }

    public boolean isReady() {
        return (printWriter != null);
    }


    private String getDayFileName(Day day) throws Exception {
        var dayName = day.getSpelledDay().orElseThrow();
        var year = day.getYear().orElseThrow();
        var fileName = String.format("%d/%s.out", year, dayName);
        return fileName;
    }

    public void submit(String s) {
        printWriter.println(s);
    }

    @Override
    public String toString() {
        return "FileOutput";
    }

}
