package com.ecommerce.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Creates and manages WebDriver instances. Uses ThreadLocal so that parallel
 * test execution (one driver per thread) works correctly.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
        // Utility class
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void initDriver() {
        if (DRIVER.get() != null) {
            return;
        }
        String browser = ConfigReader.get("browser").toLowerCase();
        String runmode = ConfigReader.get("runmode").toLowerCase();

        WebDriver driver;
        switch (runmode) {
            case "grid":
                driver = createRemoteDriver(browser);
                break;
            case "headless":
            case "local":
            default:
                driver = createLocalDriver(browser, runmode.equals("headless"));
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Long.parseLong(ConfigReader.get("implicitWait"))));
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Long.parseLong(ConfigReader.get("pageLoadTimeout"))));

        DRIVER.set(driver);
    }

    private static WebDriver createLocalDriver(String browser, boolean headless) {
        switch (browser) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("-headless");
                return new FirefoxDriver(options);
            }
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headless) options.addArguments("--headless=new");
                return new EdgeDriver(options);
            }
            case "chrome":
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-notifications");
                if (headless) options.addArguments("--headless=new");
                return new ChromeDriver(options);
            }
        }
    }

    private static WebDriver createRemoteDriver(String browser) {
        try {
            MutableCapabilities options;
            switch (browser) {
                case "firefox":
                    options = new FirefoxOptions();
                    break;
                case "edge":
                    options = new EdgeOptions();
                    break;
                case "chrome":
                default:
                    options = new ChromeOptions();
                    break;
            }
            return new RemoteWebDriver(new URL(ConfigReader.get("gridUrl")), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid grid URL", e);
        }
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
