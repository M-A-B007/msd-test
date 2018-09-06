Feature: Eshop items can be added to shopping cart
# As a quiz reviewer I want to learn how to buy some cool stuff from HW-Kitchen shop, so I can enjoy some fun.

  Scenario: Add two most expensive items in some category into shopping cart
    Given start Chrome browser
    And HW-kitchen shop page is displayed
    When navigate to section "Robots And Kits > Bot Kit"
    And filter only the goodies on stock
    And sort the goodies from most expensive
    And add 2 most expensive items to shopping cart
    Then verify the shopping cart contains 2 items