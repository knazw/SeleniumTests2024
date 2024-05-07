Feature: View of a product
  Background: Registered user
    Given following user "basicAccount" from "users.json"
    And User "basicAccount" is on register page
    And User fills all fields on registration page
    When User presses the register button
    Then Process ends with a sentence "Your registration completed"
    And User clicks continue button
    And User clicks logout button
    And User "basicAccount" is able to login on login page with login and password


  Scenario Outline: Correct user is able to view a product
    Given User on main page
    When User clicks "<productType>"
    And User selects shoes
    And User selects 1 product on page
    And User selects size of shoes: <size>
    And User selects color of shoes: "<color>"
    And User selects style: <style>
    Then Correct tooltip "<tooltip>" is displayed
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
    Examples:
      | fileName   | userId       | productType | size | color       | style | tooltip | country | postal code | shipping method |
      | users.json | basicAccount | apparel     | 8    | White/Black | 1     | Fresh   | Poland  | 823782      | 2nd Day Air     |