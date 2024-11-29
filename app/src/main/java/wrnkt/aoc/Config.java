package wrnkt.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final Logger log = LoggerFactory.getLogger(Config.class);

    Properties properties = new Properties();

    private String outputType = "console";
    private String yearPackage;

    public Config() {
        loadProperties();
    }

    public void loadProperties() {
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
            var readOutputType = properties.getProperty("runner.output");
            this.outputType = readOutputType;

            var readYearPackage = properties.getProperty("runner.scan.package");
            this.yearPackage = readYearPackage;
        } catch (IOException e) {
            log.error("Failed to load properties.");
            log.error(e.getMessage());
        }
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getYearPackage() {
        return yearPackage;
    }

    public void setYearPackage(String yearPackage) {
        this.yearPackage = yearPackage;
    }
    
}
