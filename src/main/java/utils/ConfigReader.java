package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    public static void loadConfig(String env) {
        properties = new Properties();
        String filePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "config" + File.separator + "config-" + env + ".properties";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + filePath, e);
        }
    }

    public static String getProperty(String key) {
        if (properties == null) {
            throw new RuntimeException("Config not loaded. Call loadConfig(env) first.");
        }
        return properties.getProperty(key);
    }
}
