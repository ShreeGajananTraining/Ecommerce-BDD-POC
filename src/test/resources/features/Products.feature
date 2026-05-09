@Products @Regression
Feature: Browse Products and Manage the Shopping Cart
  As a logged-in customer
  I want to view, sort, and add products to my cart
  So that I can prepare my order before checkout

  Background:
    Given the user is logged in as "standard_user" with password "secret_sauce"

  @AddToCart @Smoke
  Scenario: Add a single product to the cart
    When the user adds the product "Sauce Labs Backpack" to the cart
    Then the cart badge should show "1" item

  @AddToCart
  Scenario: Add multiple products to the cart
    When the user adds the following products to the cart:
      | Sauce Labs Backpack    |
      | Sauce Labs Bike Light  |
      | Sauce Labs Bolt T-Shirt|
    Then the cart badge should show "3" item
    When the user navigates to the cart
    Then the cart should contain the following products:
      | Sauce Labs Backpack    |
      | Sauce Labs Bike Light  |
      | Sauce Labs Bolt T-Shirt|

  @RemoveFromCart
  Scenario: Remove a product from the cart
    Given the user has added "Sauce Labs Backpack" to the cart
    When the user removes "Sauce Labs Backpack" from the cart
    Then the cart badge should not be visible
