package org.nopcommerce.pageobjects;

import org.example.pageobjects.ExtendedBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends ExtendedBasePage {

    @FindBy(id = "Email")
    @CacheLookup
    WebElement textBoxEmail;

    @FindBy(id = "Password")
    @CacheLookup
    WebElement textBoxPassword;

    @FindBy(xpath = "//button[@class='button-1 login-button']")
    @CacheLookup
    WebElement buttonLogin;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void setTextBoxEmail(String email) {
        type(textBoxEmail, email);
    }

    public void setTextBoxPassword(String password) {
        type(textBoxPassword, password);
    }

    public MainPage setButtonLogin() {
        click(buttonLogin);

        return new MainPage(driver);
    }
}
