Feature: N11 Store and Product Automation

  Background:
    Given User navigates to the N11 store page

  Scenario: Go to random store and then validate
    When User filters stores starting with letter "S"
    And User selects a random store
    Then User verifies the store page opens correctly

  Scenario: Search product, add to basket and validate
    And User searches for "iPhone"
    Then User verifies the autocomplete searching result is correct for "iPhone"
    And Press enter button
    And Adds the first and last products on the first page to cart
    Then User verifies the selected "iPhone" products added successfully to cart

  Scenario: Search product, add to basket and validate
    And User searches for "telefon"
    And Press enter button
    And User filters by the "2" brand
    And Sorts results by number of "Yorum sayısı"
    And Filters results to show only "Ücretsiz Kargo" products
    Then User verifies the result is correct according to filter
