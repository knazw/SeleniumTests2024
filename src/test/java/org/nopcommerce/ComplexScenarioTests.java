package org.nopcommerce;

import org.junit.jupiter.api.*;
import org.nopcommerce.model.Product;
import org.nopcommerce.model.ShippingMethod;
import org.nopcommerce.model.User;
import org.nopcommerce.pagefragments.Products;
import org.nopcommerce.pageobjects.*;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class ComplexScenarioTests extends BaseTest{

    static final Logger log = getLogger(lookup().lookupClass());

    MainPage mainPage;

    @BeforeEach
    void setup() {
        mainPage = new MainPage("chrome");
        mainPage.getDriver().manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        mainPage.quit();
    }


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
        registerResult.checkResult("Your registration completed");

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
                .moveBackToColor()
                .openShippingDetails();

        shippingDetailsPage = shippingDetailsPage.selectCountry(shippingMethod.country)
                .setPostalCode(shippingMethod.postalCode)
                .shippingMethodAvailable();
        shippingDetailsPage = new ShippingDetailsPage(mainPage.getDriver());
        shippingDetailsPage.checkRadioButtonShippingMethod(shippingMethod.shippingMethod)
                .applyShippingMethod();

        String shippingAddress = products.getShippingAddress();

        Assertions.assertEquals(shippingMethod.getShippingAddressFull(), shippingAddress);
    }

    @Test
    @Timeout(180)
    public void registerUserAndOrder() {
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
        registerResult.checkResult("Your registration completed");

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

        ShippingDetailsPage shippingDetailsPage = products.clickStyle(1)
                .moveBackToColor()
                .openShippingDetails();

//        mainPage =
        mainPage =  shippingDetailsPage.selectCountry(shippingMethod.country)
                .setPostalCode(shippingMethod.postalCode)
//                .shippingMethodAvailable()
                .checkRadioButtonShippingMethod(shippingMethod.shippingMethod)
                .applyShippingMethod();
//        mainPage = shippingDetailsPage.checkRadioButtonShippingMethod(shippingMethod.shippingMethod)
//                .applyShippingMethod();

        String shippingAddress = products.getShippingAddress();

        Assertions.assertEquals(shippingMethod.getShippingAddressFull(), shippingAddress);

        // ACT
        products.addToCart().getAddedToCartPopup().closePopup();
        mainPage.getHeaderUpperPage().mouseOverCartLabel();
        CartPage cartPage = products.getCartFloatingWindow().clickCart();
        BillingPage billingPage = cartPage.clickAgreement()
                .clickCheckout();

        ShippingMethodPage shippingMethodPage = billingPage
//                .waitUntilBillingAddressIsInteractable()
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
