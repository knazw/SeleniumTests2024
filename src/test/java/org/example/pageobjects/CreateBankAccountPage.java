package org.example.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.model.BankAccount;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateBankAccountPage extends ExtendedBasePage {
    // bankaccount-bankName-input
    // bankaccount-routingNumber-input
    // bankaccount-accountNumber-input
    // button[data-test='bankaccount-submit']

    @FindBy(id = "bankaccount-bankName-input")
    @CacheLookup
    WebElement banknameInput;

    @FindBy(id = "bankaccount-routingNumber-input")
    @CacheLookup
    WebElement routingNumberInput;

    @FindBy(id = "bankaccount-accountNumber-input")
    @CacheLookup
    WebElement accountNumberInput;

    @FindBy(css = "button[data-test='bankaccount-submit']")
    @CacheLookup
    WebElement buttonSave;

    /*
    public CreateBankAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public CreateBankAccountPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public UserOnBoardingPage with(BankAccount bankAccount) {
        type(banknameInput, bankAccount.bank);
        type(routingNumberInput, bankAccount.routingNumber);
        type(accountNumberInput, bankAccount.accountNr);
        click(buttonSave);

        return new UserOnBoardingPage(webDriverManager);
        //return new UserOnBoardingPage(driver);
    }


}
