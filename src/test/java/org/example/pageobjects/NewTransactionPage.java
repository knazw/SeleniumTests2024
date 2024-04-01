package org.example.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;

public class NewTransactionPage extends ExtendedBasePage {

    @FindBy(css = "h2[class='MuiTypography-root MuiTypography-h6 MuiTypography-colorPrimary MuiTypography-gutterBottom']")
    @CacheLookup
    WebElement transactionPerson;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div[2]/div/div/div[2]/div[2]/div/div/h2")
    @CacheLookup
    WebElement transactionDescription;

    @FindBy(css = "a[data-test='new-transaction-return-to-transactions']")
    @CacheLookup
    WebElement returnToTransactionsButton;

    /*
    public NewTransactionPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public NewTransactionPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public BankPage clickReturnToTransaction() {
        returnToTransactionsButton.click();

        return new BankPage(webDriverManager);
//        return new BankPage(driver);
    }

    public String getTransactionPerson() {
        return transactionPerson.getText();
    }

    public String getTransactionDescription() {
        return transactionDescription.getText();
    }

}
