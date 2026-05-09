package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the Cart page.
 */
public class CartPage extends BasePage {

    private final By cartItems = By.cssSelector(".cart_item");
    private final By cartItemNames = By.cssSelector(".inventory_item_name");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingBtn = By.id("continue-shopping");
    private final By pageTitle = By.cssSelector(".title");

    public boolean isCartPageDisplayed() {
        return isDisplayed(pageTitle) && "Your Cart".equals(getText(pageTitle));
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getCartItemNames() {
        return driver.findElements(cartItemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isProductInCart(String productName) {
        return getCartItemNames().contains(productName);
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public void clickContinueShopping() {
        click(continueShoppingBtn);
    }
}
