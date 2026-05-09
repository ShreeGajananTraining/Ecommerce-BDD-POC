package com.ecommerce.utils;

import com.ecommerce.constants.FrameworkConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads values from config.properties.
 * Loaded once (singleton-style) and reused throughout the test run.
 */
public final class ConfigReader {

    private static Properties properties;

    private ConfigReader() {
        // Utility class
    }

    private static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to load config.properties from: "
                                + FrameworkConstants.CONFIG_FILE_PATH, e);
            }
        }
    }

    public static String get(String key) {
        loadProperties();
        // System property overrides file (useful for CI: -Dbrowser=firefox)
        String sysVal = System.getProperty(key);
        if (sysVal != null && !sysVal.isEmpty()) {
            return sysVal;
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found in config.properties: " + key);
        }
        return value;
    }
}
