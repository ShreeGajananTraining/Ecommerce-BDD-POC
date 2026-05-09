# E-commerce BDD POC — Java + Selenium + Cucumber + TestNG

End-to-end Proof of Concept for automating an e-commerce application using a Behavior Driven Development framework.

**Sample Application Under Test:** [https://www.saucedemo.com/](https://www.saucedemo.com/)
*(SauceDemo is a public, stable demo site purpose-built for test automation. Use the same framework against any real e-commerce app by changing locators and `url` in `config.properties`.)*

---

## Tech Stack

| Layer              | Tool / Library                  |
|--------------------|---------------------------------|
| Language           | Java 11                         |
| Build Tool         | Maven                           |
| UI Automation      | Selenium WebDriver 4.18         |
| BDD Framework      | Cucumber 7.15 (Gherkin)         |
| Test Runner        | TestNG 7.9                      |
| Driver Management  | WebDriverManager 5.6            |
| Reporting          | Cucumber HTML + Extent (Spark)  |
| Logging            | Log4j2                          |
| Design Pattern     | Page Object Model (POM)         |

---

## Project Structure

```
ecommerce-bdd-poc/
├── pom.xml                                 # Maven config + dependencies
├── testng.xml                              # TestNG suite
├── README.md
└── src/
    ├── main/java/com/ecommerce/
    │   ├── constants/FrameworkConstants.java
    │   ├── pages/                          # Page Objects
    │   │   ├── BasePage.java
    │   │   ├── LoginPage.java
    │   │   ├── ProductsPage.java
    │   │   ├── CartPage.java
    │   │   └── CheckoutPage.java
    │   └── utils/
    │       ├── ConfigReader.java
    │       └── DriverFactory.java          # ThreadLocal driver
    └── test/
        ├── java/com/ecommerce/
        │   ├── hooks/Hooks.java            # @Before / @After
        │   ├── runners/TestRunner.java     # Cucumber options
        │   └── stepdefinitions/
        │       ├── LoginSteps.java
        │       ├── ProductSteps.java
        │       └── CheckoutSteps.java
        └── resources/
            ├── config/config.properties    # URL, browser, test data
            ├── features/                   # Gherkin .feature files
            │   ├── Login.feature
            │   ├── Products.feature
            │   └── Checkout.feature
            ├── extent.properties
            └── log4j2.xml
```

---

## Test Coverage

### 1. Login.feature
- Successful login with valid credentials (positive)
- Locked-out user, invalid creds, missing username, missing password (negative — Scenario Outline with data table)

### 2. Products.feature
- Add single product to cart and validate cart badge
- Add multiple products via Cucumber data table; assert cart contents
- Remove product and verify badge disappears

### 3. Checkout.feature
- Full end-to-end happy path: login → add product → cart → checkout (info + overview + finish) → confirmation
- Negative: missing first name / last name / postal code (Scenario Outline)

---

## Prerequisites

- **JDK 11+** (`java -version`)
- **Maven 3.6+** (`mvn -version`)
- **Chrome / Firefox / Edge** browser installed
  *(WebDriverManager auto-downloads the matching driver — no manual setup)*

---

## Quick Start

```bash
# 1. Clone / unzip the project
cd ecommerce-bdd-poc

# 2. Run the entire test suite (default: Chrome, local mode)
mvn clean test

# 3. Run only @Smoke scenarios
mvn clean test -Dcucumber.filter.tags="@Smoke"

# 4. Run only the end-to-end checkout scenario
mvn clean test -Dcucumber.filter.tags="@E2E"

# 5. Run on Firefox in headless mode
mvn clean test -Dbrowser=firefox -Drunmode=headless

# 6. Run on a remote Selenium Grid
mvn clean test -Drunmode=grid -DgridUrl=http://localhost:4444/wd/hub
```

---

## Reports & Artifacts

After the run, check the `test-output/` directory:

| File                                  | Purpose                          |
|---------------------------------------|----------------------------------|
| `cucumber-html-report.html`           | Cucumber's built-in HTML report  |
| `cucumber.json` / `cucumber.xml`      | Machine-readable for CI tooling  |
| `SparkReport/Index.html`              | Extent Spark interactive report  |
| `screenshots/`                        | Auto-captured on scenario failure|
| `logs/automation.log`                 | Log4j2 execution log             |

---

## Key Design Highlights

**Page Object Model** — Each page (Login, Products, Cart, Checkout) is its own class. Locators and actions live with the page; tests stay readable.

**ThreadLocal WebDriver** — `DriverFactory` uses `ThreadLocal<WebDriver>` so tests can be parallelized later by simply setting `parallel="true"` in `TestRunner.scenarios()`.

**Cross-browser & cross-mode** — Switch between Chrome / Firefox / Edge and local / headless / grid via a single `-D` flag. No code changes.

**Tagged scenarios** — `@Smoke`, `@Regression`, `@Positive`, `@Negative`, `@E2E` allow selective execution from CI pipelines.

**Auto screenshot on failure** — `Hooks.captureScreenshotOnFailure` attaches a screenshot to the Cucumber report whenever a scenario fails.

**Externalized config** — All test data and environment values live in `config.properties`. CLI `-Dproperty=value` flags override file values.

---

## CI/CD Integration

Sample GitHub Actions snippet:

```yaml
name: BDD Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { java-version: '11', distribution: 'temurin' }
      - name: Run BDD Smoke Suite
        run: mvn clean test -Dbrowser=chrome -Drunmode=headless -Dcucumber.filter.tags="@Smoke"
      - uses: actions/upload-artifact@v4
        if: always()
        with: { name: test-reports, path: test-output/ }
```

---

## Adapting to Your Real E-commerce App

1. Update `url` in `src/test/resources/config/config.properties` to your application's URL.
2. Update locators in the Page Object classes (`LoginPage`, `ProductsPage`, etc.) to match your DOM.
3. Update test data (usernames, passwords, products) in feature files and `config.properties`.
4. Add new `.feature` files for additional flows (search, filters, payment, profile, etc.) and matching step definitions.

The framework structure (Driver factory, Hooks, BasePage, ConfigReader, reporting) is application-agnostic and stays as-is.
