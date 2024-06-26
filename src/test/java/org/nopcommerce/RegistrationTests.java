package org.nopcommerce;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.nopcommerce.model.User;
import org.nopcommerce.pageobjects.LoginPage;
import org.nopcommerce.pageobjects.MyAccountPage;
import org.nopcommerce.pageobjects.RegisterPage;
import org.nopcommerce.pageobjects.RegisterResult;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;


public class RegistrationTests extends BaseTest{

    static final Logger log = getLogger(lookup().lookupClass());

    // todo: remove this class, it is replaced by gherkin file
    // the only plus: easily test with different browsers can be performed.

    public void write() {
        createUser();
    }

    @Tag("Fast")
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox", "edge", "chromeheadless", "firefoxheadless", "edgeheadless"})
    public void createUserAndLogin(String browser) {
        // ARRANGE
        User user = createUser();

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

        // ACT
        RegisterResult registerResult = registerPage.clickRegister();
        String registrationResult = registerResult.getRegistrationResult();
        Assertions.assertEquals("Your registration completed", registrationResult);

        mainPage = registerResult.clickContinue();

        mainPage.getHeaderUpperPage().clickLogoutLink();

        LoginPage loginPage = mainPage.clickLogin();
        loginPage.setTextBoxEmail(user.email);
        loginPage.setTextBoxPassword(user.password);
        mainPage = loginPage.setButtonLogin();

        // ASSERT

        MyAccountPage myAccountPage = mainPage.getHeaderUpperPage().clickMyAccount();
        String firstName = myAccountPage.getFirstName();
        String lastName = myAccountPage.getLastName();
        String email = myAccountPage.getEmail();

        Assertions.assertEquals(user.firstName, firstName);
        Assertions.assertEquals(user.lastName, lastName);
        Assertions.assertEquals(user.email, email);
    }

}
