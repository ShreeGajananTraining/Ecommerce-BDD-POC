@Checkout @E2E
Feature: End-to-End Checkout Flow
  As a logged-in customer with items in my cart
  I want to complete the checkout process
  So that I can place my order successfully

  Background:
    Given the user is logged in as "standard_user" with password "secret_sauce"

  @Smoke @HappyPath
  Scenario: Successful end-to-end purchase of a single product
    When the user adds the product "Sauce Labs Backpack" to the cart
    And the user navigates to the cart
    And the user proceeds to checkout
    And the user enters customer information:
      | firstName | John   |
      | lastName  | Doe    |
      | postCode  | 440001 |
    And the user clicks continue
    Then the order overview should display the subtotal
    When the user clicks finish
    Then the order confirmation message "Thank you for your order!" should be displayed

  @Negative
  Scenario Outline: Checkout fails when required information is missing
    Given the user has added "Sauce Labs Backpack" to the cart
    When the user navigates to the cart
    And the user proceeds to checkout
    And the user enters customer information:
      | firstName | <fname> |
      | lastName  | <lname> |
      | postCode  | <zip>   |
    And the user clicks continue
    Then an error message should be displayed containing "<error>"

    Examples:
      | fname | lname | zip    | error              |
      |       | Doe   | 440001 | First Name is required |
      | John  |       | 440001 | Last Name is required  |
      | John  | Doe   |        | Postal Code is required|
