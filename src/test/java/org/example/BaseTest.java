package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.model.BankAccount;
import org.example.model.User;
import org.example.pageobjects.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.nopcommerce.pageobjects.MainPage;

import static org.utilities.BaseTestHelpers.getBrowser;

public class BaseTest {

    LoginPage loginPage;

    @BeforeEach
    void setup(TestInfo info) {
        removeTestData();
        String browserName = getBrowser(info.getDisplayName());

        if(browserName == null) {
            loginPage = new LoginPage("chrome");
        }
        else {
            loginPage = new LoginPage(browserName);
        }
        loginPage.getDriver().manage().window().maximize();
    }

    @AfterEach
    void tearDown() {

        loginPage.quit();
        removeTestData();
    }

    protected void removeTestData() {
        RestAssured.given()
                .baseUri("http://localhost:3001")
                .contentType(ContentType.JSON)
            .when()
                .post("/testData/seed")
            .then()
                .statusCode(200);
    }


    protected User createUser1() {
        User user = new User();
        user.firstName = "jan";
        user.lastName = "kowalski";
        user.username = "kowalski1";
        user.password = "1234";
        user.bank = "santander";
        user.accountNr = "123456789";
        user.routingNr = "894938439";


        return user;
    }

    protected User createUser2() {
        User user2 = new User();
        user2.firstName = "adam";
        user2.lastName = "nowak";
        user2.username = "nowak2";
        user2.password = "1234";
        user2.bank = "bzwbk";
        user2.accountNr = "834748333";
        user2.routingNr = "847843033";

        return user2;
    }

    protected BankPage createUserAndBankAccount(User user) {

        CreateUserPage createUserPage = loginPage.createUser();
        loginPage = createUserPage.with(user);

        AddAccountPage addAccountPage = loginPage.withUserForTheFirstTime(user);
        CreateBankAccountPage createBankAccountPage = addAccountPage.skipPage();
        UserOnBoardingPage userOnBoardingPage = createBankAccountPage.with(BankAccount.createRandom());

        return userOnBoardingPage.clickNext();
    }
}
