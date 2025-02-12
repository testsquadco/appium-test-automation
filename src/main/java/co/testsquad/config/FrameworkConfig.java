package co.testsquad.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FrameworkConfig {
    private static final Properties properties = new Properties();
    private static FrameworkConfig instance;

    private FrameworkConfig() {
        loadConfig();
    }

    public static FrameworkConfig getInstance() {
        if (instance == null) {
            instance = new FrameworkConfig();
        }
        return instance;
    }

    private void loadConfig() {
        String env = System.getProperty("env", "qa");
        try {
            properties.load(new FileInputStream("src/main/resources/config/" + env + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
} 