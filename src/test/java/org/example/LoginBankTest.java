package org.example;

import org.example.model.*;
import org.example.pageobjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

    @Test
    void shouldCreateUserAndLogout() {
        // ARRANGE
        User user = createUser1();

        // ACT
        BankPage bankPage = createUserAndBankAccount(user);
        LoginPage loginPageAfterLogout = bankPage.logout();

        // ASSERT
        Assertions.assertTrue(loginPageAfterLogout.signInTextIsPresent());
    }

}
