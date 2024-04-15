package org.nopcommerce.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class ViewProductSteps {

    static final Logger log = getLogger(lookup().lookupClass());
    StepsData stepsData;

    public ViewProductSteps() {

    }
    public ViewProductSteps(StepsData stepsData) {
        this.stepsData = stepsData;
    }

    @Given("User on main page")
    public void UserOnMainPage() {

    }

    @When("User clicks {string}")
    public void UserClicksApparel(String linkText) {
        this.stepsData.products = this.stepsData.mainPage.getMenuItemsFragment()
                .clickApparel();
    }

    @And("User selects shoes")
    public void UserSelectsShoes() {
        this.stepsData.products
                .select();
    }

    @And("User selects {int} product on page")
    public void UserSelectsNthProductOnPage(int number) {
        this.stepsData.products
                .selectNthProductOnPage(number);
    }

    @And("User selects size of shoes: {int}")
    public void UserSelectsSizeOfShoes(int size) {
        this.stepsData.products
                .selectSize(size);
    }

    @And("User selects color of shoes: {string}")
    public void UserSelectsColorOfShoes(String color) {
        this.stepsData.products
                .selectColor(color);
    }

    @And("User selects style: {int}")
    public void UserSelectsStyle(int style) {
        this.stepsData.products
                .moveToStyle(style);
    }

    @Then("Correct tooltip {string} is displayed")
    public void CorrectTooltipIsDisplayed(String tooltip) {
        String tooltipText = this.stepsData.products.getStyleTooltip();
        Assertions.assertEquals(tooltip, tooltipText);
    }

    @And("User clicks style {int}")
    public void UserClicksStyle(int style) {
        this.stepsData.products.clickStyle(style);
    }

    @And("User moves mouse to other part of a screen")
    public void UserMovesMouseToOtherPartOfAScreen() {
        this.stepsData.products.moveToOtherPartOfAScreen();
    }

    @And("User opens shipping details")
    public void UserOpensShippingDetails() {
        this.stepsData.shippingDetailsPage =
                this.stepsData.products.openShippingDetails();
    }

    @And("User selects country {string}")
    public void UserSelectsCountry(String country) {
        this.stepsData.shippingDetailsPage.selectCountry(country);
    }

    @And("User selects postal code {int}")
    public void UserSelectsPostalCode(int postalCode) {
        this.stepsData.shippingDetailsPage.setPostalCode(postalCode + "");
    }

    @And("User waits for displayed shipping methods")
    public void UserWaitsForDisplayedShippingMethods() {
        this.stepsData.shippingDetailsPage.shippingMethodAvailable();
    }

    @And("User chooses {string}")
    public void UserChoosesShippingMethod(String shippingMethod) {
        // todo add line 75? from ComplexScenarioTests
        this.stepsData.shippingDetailsPage.checkRadioButtonShippingMethod(shippingMethod);
    }

    @And("User applies shipping method")
    public void UserAppliesShippingMethod() {
        this.stepsData.shippingDetailsPage.applyShippingMethod();
    }

    @And("It is possible to read shipping address")
    public void ItIsPossibleToReadShippingAddress() {
        this.stepsData.obtainedText =
                this.stepsData.products.getShippingAddress();
    }

    @And("Read address is equal to choosen address {string}, {int} and {string}")
    public void ReadAddressIsEqualToChoosenAddress(String country, int postalCode, String shippingMethod) {
        String baseText = "to "+country + ", "+postalCode + " via "+shippingMethod;
        Assertions.assertEquals(baseText, this.stepsData.obtainedText);
    }
    /*
//    And User moves mouse to other part of a screen
//    And User opens shipping details
//    And User selects country "<country>"
//    And User selects postal code <postal code>
//    And User waits for displayed shipping methods
//    And User chooses "<shipping method>"
//    And User applies shipping method
//    And It is possible to read shipping address
    And Read address is equal to choosen address "<country>", <postal code> and "<shipping method>"
     */

}
