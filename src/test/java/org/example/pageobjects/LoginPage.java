package org.example.pageobjects;

import org.example.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends ExtendedBasePage {

    @FindBy(id = "username")
    @CacheLookup
    WebElement usernameInput;

    @FindBy(id = "password")
    @CacheLookup
    WebElement passwordInput;

    @FindBy(css = "button[data-test='signin-submit']")
    @CacheLookup
    WebElement signInButton;

    @FindBy(css = "div[data-test='signin-error']")
    @CacheLookup
    WebElement signInErrorText;

    @FindBy(css = "a[data-test='signup']")
    @CacheLookup
    WebElement createUserLink;

    @FindBy(xpath = "//h1[@class='MuiTypography-root MuiTypography-h5']")
    @CacheLookup
    WebElement signInText;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public LoginPage(String browser, int timeoutSec) {
        this(browser, null);
        setTimeoutSec(timeoutSec);
    }

    public LoginPage(String browser) {
        super(browser);
    }

    public LoginPage(String browser, String remoteAddress) {
        super(browser);
        remoteAddressField = remoteAddress;
        PageFactory.initElements(driver, this);
        visit("http://localhost:3000");
    }

    public AddAccountPage withUserForTheFirstTime(User user) {
        By byItem = getByFromElement(usernameInput.toString());
        findWithWait(byItem, 10);

        type(usernameInput, user.username);
        type(passwordInput, user.password);
        click(signInButton);
        return new AddAccountPage(driver);
    }

    public BankPage with(User user) {
        type(usernameInput, user.username);
        type(passwordInput, user.password);
        click(signInButton);
        return new BankPage(driver);
    }

    public CreateUserPage createUser() {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        log.debug("Browser Name is : "+cap.getBrowserName());

        click(createUserLink);
        if(!cap.getBrowserName().equals("firefox")) {
            click(createUserLink);
        }
        return new CreateUserPage(this.driver);
    }

    public boolean failureBoxIsPresent() {
        return signInErrorText.isDisplayed();
    }

    public boolean signInTextIsPresent() {
        By byItem = getByFromElement(signInText.toString());
        findWithWait(byItem, 10);

        return signInText.isDisplayed();
    }


}
