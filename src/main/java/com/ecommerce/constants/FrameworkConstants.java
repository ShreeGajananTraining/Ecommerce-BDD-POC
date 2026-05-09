package com.ecommerce.constants;

/**
 * Centralized framework constants. All file paths and global values live here,
 * so future changes only need to be made in one place.
 */
public final class FrameworkConstants {

    private FrameworkConstants() {
        // Prevent instantiation
    }

    private static final String USER_DIR = System.getProperty("user.dir");

    public static final String CONFIG_FILE_PATH =
            USER_DIR + "/src/test/resources/config/config.properties";

    public static final String EXTENT_REPORT_PATH =
            USER_DIR + "/test-output/ExtentReport.html";

    public static final String SCREENSHOTS_PATH =
            USER_DIR + "/test-output/screenshots/";
}
