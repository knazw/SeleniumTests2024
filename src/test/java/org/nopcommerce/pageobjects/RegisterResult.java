package org.nopcommerce.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pageobjects.ExtendedBasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterResult extends ExtendedBasePage {

    @FindBy(css = "div[class='result']")
    @CacheLookup
    WebElement labelResult;

    @FindBy(xpath = "//a[@class='button-1 register-continue-button']")
    @CacheLookup
    WebElement buttonContinue;

    /*
    public RegisterResult(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public RegisterResult(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public String getRegistrationResult() {
        return labelResult.getText();
    }

    public MainPage clickContinue() {
        click(buttonContinue);

        return new MainPage(webDriverManager);
        //return new MainPage(driver);
    }
}
