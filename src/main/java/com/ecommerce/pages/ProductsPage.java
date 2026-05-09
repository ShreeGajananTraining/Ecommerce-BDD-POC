package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the Products listing page (post-login landing page).
 */
public class ProductsPage extends BasePage {

    // Locators
    private final By pageTitle = By.cssSelector(".title");
    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By cartIcon = By.cssSelector(".shopping_cart_link");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");

    public boolean isProductsPageDisplayed() {
        return isDisplayed(pageTitle) && "Products".equals(getText(pageTitle));
    }

    public int getProductCount() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(inventoryItems));
        return driver.findElements(inventoryItems).size();
    }

    public List<String> getAllProductNames() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemNames));
        return driver.findElements(itemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void addProductToCart(String productName) {
        // Convert product name to data-test format used by Sauce Demo
        // e.g. "Sauce Labs Backpack" -> "add-to-cart-sauce-labs-backpack"
        String formatted = productName.toLowerCase().replace(" ", "-");
        By addButton = By.cssSelector("[data-test='add-to-cart-" + formatted + "']");
        click(addButton);
    }

    public void removeProductFromCart(String productName) {
        String formatted = productName.toLowerCase().replace(" ", "-");
        By removeButton = By.cssSelector("[data-test='remove-" + formatted + "']");
        click(removeButton);
    }

    public int getCartCount() {
        if (!isDisplayed(cartBadge)) {
            return 0;
        }
        return Integer.parseInt(getText(cartBadge));
    }

    public void clickCart() {
        click(cartIcon);
    }

    public void sortProducts(String option) {
        WebElement dropdown = waitForVisible(sortDropdown);
        new Select(dropdown).selectByVisibleText(option);
    }
}
