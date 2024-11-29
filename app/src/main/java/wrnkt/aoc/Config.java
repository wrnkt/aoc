package wrnkt.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    Properties properties = new Properties();

    private String outputType = "console";

    public Config() {
        loadProperties();
    }

    public void loadProperties() {
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
            var readOutputType = properties.getProperty("runner.output");
            this.outputType = readOutputType;
        } catch (IOException e) {
        }
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    
}
