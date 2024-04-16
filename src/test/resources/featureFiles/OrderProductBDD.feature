Feature: View and order of a product
  Background: Registered user
    Given following user "basicAccount" from "users.json"
    And User "basicAccount" is on register page
    And User fills all fields on registration page
    When User presses the register button
    Then Process ends with a sentence "Your registration completed"
    And User clicks continue button
    And User "basicAccount" is able to login on login page with login and password


  Scenario Outline: Correct user is able to view and order of a product
    Given User on main page
    And User clicks "<productType>"
    And User selects shoes
    And User selects 1 product on page
    And User selects size of shoes: <size>
    And User selects color of shoes: "<color>"
    And User selects style: <style>
    And Correct tooltip "<tooltip>" is displayed
    And User clicks style <style>
    And User moves mouse to other part of a screen
    And User opens shipping details
    And User selects country "<country>"
    And User selects postal code <postal code>
    And User waits for displayed shipping methods
    And User chooses "<shipping method>"
    And User applies shipping method
    And It is possible to read shipping address
    And Read address is equal to choosen address "<country>", <postal code> and "<shipping method>"
    When User clicks add to cart
    And User closes a 'added to cart' popup
    And User moves mouse over cart label in right upper corner
    And User clicks this cart in right upper corner
    And User clicks agreement on a cart page
    And User clicks checkout on a cart page
    And User fills all fields on billing page
    And User clicks continue button on billing page
    And User chooses shipping method on shipping method page
    And User clicks continue button on shipping method page
    And User chooses credit card on payment method page
    And User fills all fields on payment info page
    And User clicks continue button on payment info page
    And User clicks confirm order on confirm order page
    And User clicks order details link on checkout completed page
    Then Obtained email on order detail page is equal to users email
    And Obtained color on order detail page is eqaul to color "<color>" from users order


    Examples:
      | fileName   | userId       | productType | size | color       | style | tooltip | country | postal code | shipping method |
      | users.json | basicAccount | apparel     | 8    | White/Black | 1     | Fresh   | Poland  | 823782      | 2nd Day Air     |