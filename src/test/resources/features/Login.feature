@Login @Smoke
Feature: User Login to E-commerce Application
  As a customer of the e-commerce site
  I want to log in to my account
  So that I can browse and purchase products

  Background:
    Given the user is on the login page

  @Positive
  Scenario: Successful login with valid credentials
    When the user enters username "standard_user" and password "secret_sauce"
    And the user clicks the login button
    Then the user should be navigated to the products page
    And the products page should display at least 6 products

  @Negative
  Scenario Outline: Login attempts with invalid credentials show an error
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then an error message should be displayed containing "<error>"

    Examples:
      | username        | password      | error                              |
      | locked_out_user | secret_sauce  | locked out                         |
      | invalid_user    | invalid_pass  | do not match                       |
      |                 | secret_sauce  | Username is required               |
      | standard_user   |               | Password is required               |
