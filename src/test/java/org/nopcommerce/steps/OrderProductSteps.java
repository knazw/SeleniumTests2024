package org.nopcommerce.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.nopcommerce.pagefragments.Products;
import org.nopcommerce.pageobjects.CheckoutCompletedPage;
import org.nopcommerce.pageobjects.ShippingMethodPage;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class OrderProductSteps {
    static final Logger log = getLogger(lookup().lookupClass());
    StepsData stepsData;

    public OrderProductSteps() {

    }
    public OrderProductSteps(StepsData stepsData) {
        this.stepsData = stepsData;
    }

    @When("User clicks add to cart")
    public void UserClicksAddToCart() {
        this.stepsData.products = new Products(this.stepsData.products.getWebDriverManager());
        this.stepsData.products.addToCart();
    }

    @And("User closes a 'added to cart' popup")
    public void UserClosesAddedToCartPopup() {
        this.stepsData.products.getAddedToCartPopup().closePopup();
    }

    @And("User moves mouse over cart label in right upper corner")
    public void UserMovesMouseOverCartLabelInRightUpperCorner() {
        this.stepsData.mainPage.getHeaderUpperPage().mouseOverCartLabel();
    }

    @And("User clicks this cart in right upper corner")
    public void UserClicksThisCartInRightUpperCorner() {
        this.stepsData.cartPage = this.stepsData.products.getCartFloatingWindow().clickCart();
    }

    @And("User clicks agreement on a cart page")
    public void UserClicksAgreementOnACartPage() {
        this.stepsData.cartPage.clickAgreement();
    }

    @And("User clicks checkout on a cart page")
    public void UserClicksCheckoutOnACartPage() {
        this.stepsData.billingPage = this.stepsData.cartPage.clickCheckout();
    }

    @And("User fills all fields on billing page")
    public void UserFillsAllFieldsOnBillingPage() {
        this.stepsData.billingPage
                .setCity(this.stepsData.currentUser.city)
                .setCountry(this.stepsData.currentUser.country)
                .setAddress1(this.stepsData.currentUser.address)
                .postalCode(this.stepsData.currentUser.postalCode)
                .phoneNumber(this.stepsData.currentUser.phoneNumber);
    }

    @And("User clicks continue button on billing page")
    public void UserClicksContinueButtonOnBillingPage() {
        this.stepsData.shippingMethodPage = this.stepsData.billingPage
                .clickContinueButton();
    }

    @And("User chooses shipping method on shipping method page")
    public void UserChoosesShippingMethodOnShippingMethodPage() {
        this.stepsData.shippingMethodPage.clickShippingMethod();
    }

    @And("User clicks continue button on shipping method page")
    public void UserClicksContinueButtonOnShippingMethodPage() {
        this.stepsData.paymentMethodPage =
                this.stepsData.shippingMethodPage.clickContinueButton();
    }

    @And("User chooses credit card on payment method page")
    public void UserChoosesCreditCardOnPaymentMethodPage() {
        this.stepsData.paymentInfoPage = this.stepsData.paymentMethodPage
                .clickCreditCard()
                .clickContinueButton();
    }

    @And("User fills all fields on payment info page")
    public void UserFillsAllFieldsOnPaymentInfoPage() {
        this.stepsData.paymentInfoPage
                .fillCardholderName(this.stepsData.currentUser.firstName + " "+this.stepsData.currentUser.lastName)
                .fillCardNumber(this.stepsData.currentUser.creditCard)
                .fillExpirationMonth("06")
                .fillCardCode(this.stepsData.currentUser.verificationCode);
    }

    @And("User clicks continue button on payment info page")
    public void UserClicksContinueButtonOnPaymentInfoPage() {
        this.stepsData.confirmOrderPage =
                this.stepsData.paymentInfoPage.clickContinueButton();
    }

    @And("User clicks confirm order on confirm order page")
    public void UserClicksConfirmOrderOnConfirmOrderPage() {
        this.stepsData.checkoutCompletedPage =
                this.stepsData.confirmOrderPage.clickConfirmOrder();
    }

    @And("User clicks order details link on checkout completed page")
    public void UserClicksOrderDetailsLinkOnCheckoutCompletedPage() {
        this.stepsData.orderDetailsPage =
                this.stepsData.checkoutCompletedPage.clickOrderDetailsLink();
    }

    @Then("Obtained email on order detail page is equal to users email")
    public void ObtainedEmailOnOrderDetailPageIsEqualToUsersEmail() {
        String obtainedEmail = this.stepsData.orderDetailsPage.getEmail();
        Assertions.assertEquals(this.stepsData.currentUser.email, obtainedEmail);
    }

    @And("Obtained color on order detail page is eqaul to color {string} from users order")
    public void ObtainedColorOnOrderDetailPageIsEqaulToColorFromUsersOrder(String color) {
        String obtainedColor = this.stepsData.orderDetailsPage.getColor();
        Assertions.assertEquals(color, obtainedColor);
    }

    /*
//        When User clicks add to cart
//    And User closes a 'added to cart' popup
//    And User moves mouse over cart label in right upper corner
//    And User clicks this cart in right upper corner
//    And User clicks agreement on a cart page
//    And User clicks checkout on a cart page
//    And User fills all fields on billing page
//    And User clicks continue button on billing page
//    And User chooses shipping method on shipping method page
//    And User clicks continue button on shipping method page
//    And User fills all fields on payment info page
//      And User clicks continue button on payment info page
//    And User clicks confirm order on confirm order page
//    And User clicks order details link on checkout completed page
//    Then Obtained email on order detail page is equal to users email
    And Obtained color on order detail page is eqaul to color from users order
     */
}
