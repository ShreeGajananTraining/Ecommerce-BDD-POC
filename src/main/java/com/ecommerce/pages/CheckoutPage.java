package com.ecommerce.pages;

import org.openqa.selenium.By;

/**
 * Page Object for the multi-step Checkout flow:
 *  Step 1: Personal information
 *  Step 2: Order overview
 *  Step 3: Confirmation
 */
public class CheckoutPage extends BasePage {

    // Step 1 - Information
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    // Step 2 - Overview
    private final By finishBtn = By.id("finish");
    private final By subtotalLabel = By.cssSelector(".summary_subtotal_label");

    // Step 3 - Confirmation
    private final By confirmationHeader = By.cssSelector(".complete-header");
    private final By backHomeBtn = By.id("back-to-products");

    public void enterCustomerInfo(String fname, String lname, String zip) {
        type(firstName, fname);
        type(lastName, lname);
        type(postalCode, zip);
    }

    public void clickContinue() {
        click(continueBtn);
    }

    public void clickFinish() {
        click(finishBtn);
    }

    public String getOrderSubtotal() {
        return getText(subtotalLabel);
    }

    public String getConfirmationMessage() {
        return getText(confirmationHeader);
    }

    public boolean isOrderConfirmed() {
        return isDisplayed(confirmationHeader);
    }

    public void clickBackHome() {
        click(backHomeBtn);
    }
}
