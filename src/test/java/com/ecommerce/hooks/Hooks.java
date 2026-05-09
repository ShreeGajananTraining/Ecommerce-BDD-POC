package com.ecommerce.hooks;

import com.ecommerce.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber hooks: run before/after each scenario.
 *  - @Before: spin up a fresh browser instance
 *  - @After: capture screenshot on failure, then quit driver
 */
public class Hooks {

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("==> Starting scenario: " + scenario.getName());
        DriverFactory.initDriver();
    }

    @After(order = 1)
    public void captureScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                WebDriver driver = DriverFactory.getDriver();
                if (driver != null) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", scenario.getName());
                }
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        System.out.println("==> Finished scenario: " + scenario.getName()
                + " | Status: " + scenario.getStatus());
        DriverFactory.quitDriver();
    }
}
