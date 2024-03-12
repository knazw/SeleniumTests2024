package org.example.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddAccountPage extends ExtendedBasePage {

    @FindBy(css = "button[data-test='user-onboarding-next']")
    @CacheLookup
    WebElement nextButton;

    public AddAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CreateBankAccountPage skipPage() {
        click(nextButton);
        return new CreateBankAccountPage(driver);
    }

}
