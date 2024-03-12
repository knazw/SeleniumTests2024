package org.example.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class TransactionDetailPage extends ExtendedBasePage {

    @FindBy(css = "button[data-test*='transaction-accept-request-'")
    @CacheLookup
    List<WebElement> acceptRequest;

    @FindBy(css = "span[data-test*='transaction-sender-']")
    @CacheLookup
    List<WebElement> transactionSender;

    @FindBy(css = "span[data-test*='transaction-action-']")
    @CacheLookup
    List<WebElement> transactionAction;

    @FindBy(css = "span[data-test*='transaction-receiver-']")
    @CacheLookup
    List<WebElement> transactionReceiver;


    public TransactionDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void acceptRequest() {
        click(acceptRequest.get(0));
    }

    public String getSender(int index) {
        return transactionSender.get(index).getText();
    }

    public String getReceiver(int index) {
        return transactionReceiver.get(index).getText();
    }

    public String getAction(int index) {
        return transactionAction.get(index).getText();
    }
}
