package org.nopcommerce.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.nopcommerce.BaseTest;
import org.nopcommerce.helpers.FileOperations;
import org.nopcommerce.model.User;
import org.nopcommerce.pageobjects.MainPage;
import org.nopcommerce.pageobjects.RegisterPage;
import org.slf4j.Logger;


import java.util.HashMap;
import java.util.Map;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class RegistrationSteps extends BaseTest {

    static final Logger log = getLogger(lookup().lookupClass());
    StepsData stepsData;

    @After(order=1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        log.debug("\n\n\n============take screenshot============\n\n\n");

        if(scenario.isFailed()) {
            makeScreenshotAfterFail(scenario.getName());
        }
    }

    @After(order=0)
    public void afterEach() {
        log.debug("\n\n\n============after each============\n\n\n");

        if(mainPage != null) {
            mainPage.quit();
        }
    }

    @Before
    public void beforeEach() {
        log.debug("\n\n\n============before each============\n\n\n");

        // todo: read from properties
        String browserName = "firefox";

        if(browserName == null) {
            mainPage = new MainPage("chrome");
        }
        else {
            mainPage = new MainPage(browserName);
        }
        mainPage.getDriver().manage().window().maximize();
    }


    @BeforeAll
    public static void setupAll() {
        setupBeforeAll();
    }

    public RegistrationSteps() {

    }
    public RegistrationSteps(StepsData stepsData) {
        this.stepsData = stepsData;
    }

    @Given("following user {string} from {string}")
    public void FollowingUser(String username, String filename) {
        String json = FileOperations.readFromFile(filename);
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Map<String,User> users;

        try {
            users = objectMapper.readValue(json, new TypeReference<Map<String,User>>() {});

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User user = users.get(username);
        user.email = changeEmail(users.get(username).email);

        FileOperations.writeToFile(user, filename);

        this.stepsData.UsersIdMap = users;
        this.stepsData.currentUser = user;

        FileOperations.writeToFile(users, filename);
    }

    @And("User {string} is on register page")
    public void UserIsOnRegisterPage(String email) {

        this.stepsData.registerPage = mainPage.clickRegister();
    }

    @And("User fills all fields on registration page")
    public void UserFillsAllFieldsOnRegistrationPage() {

        this.stepsData.registerPage.selectMaleOrFemale(this.stepsData.currentUser.gender);
        this.stepsData.registerPage.setFirstName(this.stepsData.currentUser.firstName);
        this.stepsData.registerPage.setLastName(this.stepsData.currentUser.lastName);

        this.stepsData.registerPage.setDayOfBirth(this.stepsData.currentUser.dateOfBirth.getDayOfMonth());
        this.stepsData.registerPage.setMonthOfBirth(this.stepsData.currentUser.dateOfBirth.getMonth().toString());
        this.stepsData.registerPage.setYearOfBirth(this.stepsData.currentUser.dateOfBirth.getYear());
        this.stepsData.registerPage.setEmail(this.stepsData.currentUser.email);

        this.stepsData.registerPage.setInputPassword(this.stepsData.currentUser.password);
        this.stepsData.registerPage.setInputConfirmPassword(this.stepsData.currentUser.password);
        this.stepsData.registerPage.setCheckboxNewsletter(this.stepsData.currentUser.newsletter);

    }

    @When("User presses the register button")
    public void UserPressesTheRegisterButton() {
        this.stepsData.registerResult = this.stepsData.registerPage.clickRegister();
    }

    @Then("Process ends with a sentence {string}")
    public void ProcessEndsWithASentence(String sentence) {
        String registrationResult = this.stepsData.registerResult.getRegistrationResult();
        Assertions.assertEquals(sentence, registrationResult);
    }

    @And("User clicks continue button")
    public void UserClicksContinueButton() {
        this.stepsData.mainPage = this.stepsData.registerResult.clickContinue();
    }

    @And("User {string} is able to login on login page with login and password")
    public void UserIsAbleToLoginOnLoginPageWithLoginAndPassword(String username) {
        this.stepsData.loginPage = this.stepsData.mainPage.clickLogin();
        this.stepsData.loginPage.setTextBoxEmail(this.stepsData.currentUser.email);
        this.stepsData.loginPage.setTextBoxPassword(this.stepsData.currentUser.password);
        this.stepsData.mainPage = this.stepsData.loginPage.setButtonLogin();
    }

    @And("Correct data are displayed in user page")
    public void CorrectDataAreStoredInUserPage() {
        this.stepsData.myAccountPage = mainPage.getHeaderUpperPage().clickMyAccount();
        String firstName = this.stepsData.myAccountPage.getFirstName();
        String lastName = this.stepsData.myAccountPage.getLastName();
        String email = this.stepsData.myAccountPage.getEmail();

        Assertions.assertEquals(this.stepsData.currentUser.firstName, firstName);
        Assertions.assertEquals(this.stepsData.currentUser.lastName, lastName);
        Assertions.assertEquals(this.stepsData.currentUser.email, email);
    }
}
