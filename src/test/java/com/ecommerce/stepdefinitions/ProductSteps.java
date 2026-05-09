package com.ecommerce.stepdefinitions;

import com.ecommerce.pages.CartPage;
import com.ecommerce.pages.ProductsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class ProductSteps {

    private final ProductsPage productsPage = new ProductsPage();
    private final CartPage cartPage = new CartPage();

    @When("the user adds the product {string} to the cart")
    public void theUserAddsTheProductToTheCart(String product) {
        productsPage.addProductToCart(product);
    }

    @Given("the user has added {string} to the cart")
    public void theUserHasAddedToTheCart(String product) {
        productsPage.addProductToCart(product);
    }

    @When("the user adds the following products to the cart:")
    public void theUserAddsTheFollowingProductsToTheCart(List<String> products) {
        for (String product : products) {
            productsPage.addProductToCart(product);
        }
    }

    @Then("the cart badge should show {string} item")
    public void theCartBadgeShouldShowItem(String expectedCount) {
        Assert.assertEquals(String.valueOf(productsPage.getCartCount()), expectedCount,
                "Cart badge count mismatch");
    }

    @Then("the cart badge should not be visible")
    public void theCartBadgeShouldNotBeVisible() {
        Assert.assertEquals(productsPage.getCartCount(), 0,
                "Cart badge should not be visible (cart should be empty)");
    }

    @When("the user removes {string} from the cart")
    public void theUserRemovesFromTheCart(String product) {
        productsPage.removeProductFromCart(product);
    }

    @And("the user navigates to the cart")
    @When("the user navigates to the cart")
    public void theUserNavigatesToTheCart() {
        productsPage.clickCart();
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed");
    }

    @Then("the cart should contain the following products:")
    public void theCartShouldContainTheFollowingProducts(List<String> expected) {
        List<String> actual = cartPage.getCartItemNames();
        Assert.assertTrue(actual.containsAll(expected),
                "Cart contents mismatch. Expected: " + expected + " | Actual: " + actual);
    }
}
