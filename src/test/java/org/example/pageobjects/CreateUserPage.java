package org.example.pageobjects;

import org.example.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateUserPage extends ExtendedBasePage {

    @FindBy(id = "firstName")
    @CacheLookup
    WebElement firstNameInput;

    @FindBy(id = "lastName")
    @CacheLookup
    WebElement lastNameInput;

    @FindBy(id = "username")
    @CacheLookup
    WebElement usernameInput;

    @FindBy(id = "password")
    @CacheLookup
    WebElement passwordInput;

    @FindBy(id = "confirmPassword")
    @CacheLookup
    WebElement confirmPasswordInput;

    @FindBy(css = "button[data-test='signup-submit']")
    @CacheLookup
    WebElement buttonSignUp;


    public CreateUserPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CreateUserPage(String browser, int timeoutSec) {
        this(browser);
        setTimeoutSec(timeoutSec);
    }

    public CreateUserPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);

    }

    public LoginPage with(User user) {
        type(firstNameInput, user.firstName);
        type(lastNameInput, user.lastName);
        type(usernameInput, user.username);
        type(passwordInput, user.password);
        type(confirmPasswordInput, user.password);
        click(buttonSignUp);
        return new LoginPage(driver);
    }

}
