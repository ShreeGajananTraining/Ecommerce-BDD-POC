package com.ecommerce.stepdefinitions;

import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.ProductsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final ProductsPage productsPage = new ProductsPage();

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page is not displayed");
        System.out.println();
    }

    @Given("the user is logged in as {string} with password {string}")
    public void theUserIsLoggedInAsWithPassword(String user, String pass) {
        loginPage.navigateToLoginPage();
        loginPage.loginAs(user, pass);
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "User did not land on the Products page after login");
    }

    @When("the user enters username {string} and password {string}")
    public void theUserEntersUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("the user should be navigated to the products page")
    public void theUserShouldBeNavigatedToTheProductsPage() {
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "User was not navigated to Products page");
    }

    @Then("the products page should display at least {int} products")
    public void theProductsPageShouldDisplayAtLeastProducts(int min) {
        int actual = productsPage.getProductCount();
        Assert.assertTrue(actual >= min,
                "Expected at least " + min + " products, but found " + actual);
    }

    @Then("an error message should be displayed containing {string}")
    public void anErrorMessageShouldBeDisplayedContaining(String expected) {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed");
        String actual = loginPage.getErrorMessage();
        Assert.assertTrue(actual.toLowerCase().contains(expected.toLowerCase()),
                "Expected error to contain [" + expected + "] but was [" + actual + "]");
        
    }
}
