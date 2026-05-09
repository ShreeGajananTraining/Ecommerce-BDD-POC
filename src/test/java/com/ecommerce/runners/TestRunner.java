package com.ecommerce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Cucumber + TestNG runner. Wires feature files to step definitions and hooks,
 * configures the reporting plugins.
 *
 * To run a specific tag from CLI:
 *   mvn test -Dcucumber.filter.tags="@Smoke"
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.ecommerce.stepdefinitions",
                "com.ecommerce.hooks"
        },
        plugin = {
                "pretty",
                "html:test-output/cucumber-html-report.html",
                "json:test-output/cucumber.json",
                "junit:test-output/cucumber.xml",
                "com.aventstack.chaintest.plugins.ChainTestCucumberListener:test-output/chaintest/Index.html"
        },
        monochrome = true,
        publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Enable parallel scenario execution by overriding scenarios().
     * Set parallel="true" if needed; off by default for the POC.
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
