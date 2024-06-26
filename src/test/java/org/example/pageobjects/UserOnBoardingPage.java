package org.example.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserOnBoardingPage extends ExtendedBasePage {

    @FindBy(css = "button[data-test='user-onboarding-next']")
    @CacheLookup
    WebElement nextPageButton;

    /*
    public UserOnBoardingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public UserOnBoardingPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public BankPage clickNext() {
        click(nextPageButton);

        return new BankPage(webDriverManager);
        //return new BankPage(driver);
    }
}
