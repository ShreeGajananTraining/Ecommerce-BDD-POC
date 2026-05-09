package com.ecommerce.stepdefinitions;

import com.ecommerce.pages.CartPage;
import com.ecommerce.pages.CheckoutPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.ecommerce.utils.DriverFactory;
import org.testng.Assert;

import java.util.Map;

public class CheckoutSteps {

    private final CartPage cartPage = new CartPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @And("the user proceeds to checkout")
    @When("the user proceeds to checkout")
    public void theUserProceedsToCheckout() {
        cartPage.clickCheckout();
    }

    @And("the user enters customer information:")
    @When("the user enters customer information:")
    public void theUserEntersCustomerInformation(Map<String, String> info) {
        checkoutPage.enterCustomerInfo(
                info.getOrDefault("firstName", ""),
                info.getOrDefault("lastName", ""),
                info.getOrDefault("postCode", ""));
    }

    @And("the user clicks continue")
    @When("the user clicks continue")
    public void theUserClicksContinue() {
        checkoutPage.clickContinue();
    }

    @Then("the order overview should display the subtotal")
    public void theOrderOverviewShouldDisplayTheSubtotal() {
        String subtotal = checkoutPage.getOrderSubtotal();
        Assert.assertNotNull(subtotal, "Order subtotal not displayed");
        Assert.assertTrue(subtotal.toLowerCase().contains("item total"),
                "Subtotal label not as expected: " + subtotal);
    }

    @When("the user clicks finish")
    public void theUserClicksFinish() {
        checkoutPage.clickFinish();
    }

    @Then("the order confirmation message {string} should be displayed")
    public void theOrderConfirmationMessageShouldBeDisplayed(String expected) {
        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "Order confirmation page not displayed");
        Assert.assertEquals(checkoutPage.getConfirmationMessage(), expected,
                "Confirmation message mismatch");
    }

    /**
     * Reusing the same step phrasing as login error step.
     * The checkout page also surfaces an [data-test='error'] element
     * so we read it directly here.
     */
    @Then("an error message should be displayed containing {string} on checkout")
    public void anErrorMessageShouldBeDisplayedContainingOnCheckout(String expected) {
        WebDriver driver = DriverFactory.getDriver();
        String actual = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(actual.toLowerCase().contains(expected.toLowerCase()),
                "Expected error to contain [" + expected + "] but was [" + actual + "]");
    }
}
