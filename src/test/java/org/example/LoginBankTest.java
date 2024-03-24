package org.example;

import org.example.model.*;
import org.example.pageobjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class LoginBankTest extends BaseTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void testLoginFail() {
        // ARRANGE
        User user = createUser1();

        // ACT
        loginPage.withUserForTheFirstTime(user);

        // ASSERT
        assertThat(loginPage.failureBoxIsPresent()).isTrue();
    }

    @Tag("Registration")
    @Tag("Fast")
    @ParameterizedTest
    @ValueSource(strings = {"firefox", "edge", "chromeheadless", "firefoxheadless", "edgeheadless"})
//    @ValueSource(strings = {"edge"})
    void shouldCreateUserAndLogout(String browser) {
        // ARRANGE
        User user = createUser1();

        // ACT
        BankPage bankPage = createUserAndBankAccount(user);
        LoginPage loginPageAfterLogout = bankPage.logout();

        // ASSERT
        Assertions.assertTrue(loginPageAfterLogout.signInTextIsPresent());
    }

}
