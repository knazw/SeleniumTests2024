package org.nopcommerce;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.nopcommerce.model.Product;
import org.nopcommerce.model.ShippingMethod;
import org.nopcommerce.model.User;
import org.nopcommerce.pagefragments.Products;
import org.nopcommerce.pageobjects.*;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class ComplexScenarioTests extends BaseTest{

    // todo: remove this class, it is replaced by gherkin file
    // the only plus: easily test with different browsers can be performed.
    static final Logger log = getLogger(lookup().lookupClass());

    @Tag("Fast")
    @Test
    @Timeout(180)
    public void registerUserAndViewProduct() {
        // ARRANGE
        User user = createUser();
        Product product = createProduct();
        ShippingMethod shippingMethod = createShippingMethod();

        RegisterPage registerPage = mainPage.clickRegister();

        registerPage.selectMaleOrFemale(user.gender);
        registerPage.setFirstName(user.firstName);
        registerPage.setLastName(user.lastName);

        registerPage.setDayOfBirth(user.dateOfBirth.getDayOfMonth());
        registerPage.setMonthOfBirth(user.dateOfBirth.getMonth().toString());
        registerPage.setYearOfBirth(user.dateOfBirth.getYear());
        registerPage.setEmail(user.email);

        registerPage.setInputPassword(user.password);
        registerPage.setInputConfirmPassword(user.password);
        registerPage.setCheckboxNewsletter(user.newsletter);

        RegisterResult registerResult = registerPage.clickRegister();
        String registrationResult = registerResult.getRegistrationResult();
        Assertions.assertEquals("Your registration completed", registrationResult);

        mainPage = registerResult.clickContinue();

        LoginPage loginPage = mainPage.clickLogin();
        loginPage.setTextBoxEmail(user.email);
        loginPage.setTextBoxPassword(user.password);
        mainPage = loginPage.setButtonLogin();

        // ACT
        Products products = mainPage.getMenuItemsFragment()
                .clickApparel()
                .select()
                .selectNthProductOnPage(1)
                .selectSize(product.size)
                .selectColor(product.color)
                .moveToStyle(1);

        // ASSERT
        String tooltipText = products.getStyleTooltip();
        Assertions.assertEquals("Fresh", tooltipText);

        ShippingDetailsPage shippingDetailsPage = products.clickStyle(1)
                .moveToOtherPartOfAScreen()
                .openShippingDetails();

        shippingDetailsPage = shippingDetailsPage.selectCountry(shippingMethod.country)
                .setPostalCode(shippingMethod.postalCode)
                .shippingMethodAvailable();
        shippingDetailsPage = new ShippingDetailsPage(mainPage.getWebDriverManager());
        shippingDetailsPage.checkRadioButtonShippingMethod(shippingMethod.shippingMethod)
                .applyShippingMethod();

        String shippingAddress = products.getShippingAddress();

        Assertions.assertEquals(shippingMethod.getShippingAddressFull(), shippingAddress);
    }


    @Tag("Slow")
    @Timeout(180)
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox", "edge", "chromeheadless", "firefoxheadless"})
    public void registerUserAndOrder(String browser) {
        // ARRANGE
        User user = createUser();
        Product product = createProduct();
        ShippingMethod shippingMethod = createShippingMethod();

        RegisterPage registerPage = mainPage.clickRegister();

        registerPage.selectMaleOrFemale(user.gender);
        registerPage.setFirstName(user.firstName);
        registerPage.setLastName(user.lastName);

        registerPage.setDayOfBirth(user.dateOfBirth.getDayOfMonth());
        registerPage.setMonthOfBirth(user.dateOfBirth.getMonth().toString());
        registerPage.setYearOfBirth(user.dateOfBirth.getYear());
        registerPage.setEmail(user.email);

        registerPage.setInputPassword(user.password);
        registerPage.setInputConfirmPassword(user.password);
        registerPage.setCheckboxNewsletter(user.newsletter);

        RegisterResult registerResult = registerPage.clickRegister();
        String registrationResult = registerResult.getRegistrationResult();
        Assertions.assertEquals("Your registration completed", registrationResult);

        mainPage = registerResult.clickContinue();

        LoginPage loginPage = mainPage.clickLogin();
        loginPage.setTextBoxEmail(user.email);
        loginPage.setTextBoxPassword(user.password);
        mainPage = loginPage.setButtonLogin();

        Products products = mainPage.getMenuItemsFragment()
                .clickApparel()
                .select()
                .selectNthProductOnPage(1)
                .selectSize(product.size)
                .selectColor(product.color)
                .moveToStyle(1);

        String tooltipText = products.getStyleTooltip();
        Assertions.assertEquals("Fresh", tooltipText);

        ShippingDetailsPage shippingDetailsPage = products.clickStyle(1)  // hide tooltip
                .moveToOtherPartOfAScreen()
                .openShippingDetails();

        mainPage =  shippingDetailsPage.selectCountry(shippingMethod.country)
                .setPostalCode(shippingMethod.postalCode)
                .checkRadioButtonShippingMethod(shippingMethod.shippingMethod)
                .applyShippingMethod();

        String shippingAddress = products.getShippingAddress();

        Assertions.assertEquals(shippingMethod.getShippingAddressFull(), shippingAddress);

        // ACT
        products = new Products(products.getWebDriverManager()); // refresh references
        products.addToCart().getAddedToCartPopup().closePopup();
        mainPage.getHeaderUpperPage().mouseOverCartLabel();
        CartPage cartPage = products.getCartFloatingWindow().clickCart();
        BillingPage billingPage = cartPage.clickAgreement()
                .clickCheckout();

        ShippingMethodPage shippingMethodPage = billingPage
                .setCity(user.city)
                .setCountry(user.country)
                .setAddress1(user.address)
                .postalCode(user.postalCode)
                .phoneNumber(user.phoneNumber)
                .clickContinueButton();

        PaymentMethodPage paymentMethodPage = shippingMethodPage.clickShippingMethod()
                .clickContinueButton();

        PaymentInfoPage paymentInfoPage = paymentMethodPage.clickCreditCard()
                .clickContinueButton();

        ConfirmOrderPage confirmOrderPage = paymentInfoPage.fillCardholderName(user.firstName + " "+user.lastName)
                .fillCardNumber(user.creditCard)
                .fillExpirationMonth("06")
                .fillCardCode(user.verificationCode)
                .clickContinueButton();

        CheckoutCompletedPage checkoutCompletedPage = confirmOrderPage.clickConfirmOrder();
        OrderDetailsPage orderDetailsPage = checkoutCompletedPage.clickOrderDetailsLink();

        // ASSERT
        String obtainedEmail = orderDetailsPage.getEmail();
        String obtainedColor = orderDetailsPage.getColor();

        Assertions.assertEquals(user.email, obtainedEmail);
        Assertions.assertEquals(product.color, obtainedColor);

    }

}
