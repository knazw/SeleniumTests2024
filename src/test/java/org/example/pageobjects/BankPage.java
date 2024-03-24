package org.example.pageobjects;

import org.example.model.*;
import org.nopcommerce.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;


import java.util.List;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BankPage extends ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(css = "div[data-test='transaction-list-filter-amount-range-button']")
    @CacheLookup
    WebElement transactionFilter;

    @FindBy(css = "h6[data-test='sidenav-user-balance']")
    @CacheLookup
    WebElement accountBalance;

    @FindBy(css = "div[data-test='sidenav-signout']")
    @CacheLookup
    WebElement logoutButton;

    @FindBy(css = "a[data-test='nav-personal-tab']")
    @CacheLookup
    WebElement mineTransactions;

    @FindBy(css = "a[data-test='nav-top-new-transaction']")
    @CacheLookup
    WebElement newTransaction;

    @FindBy(id = "amount")
    @CacheLookup
    WebElement amountInput;

    @FindBy(id = "transaction-create-description-input")
    @CacheLookup
    WebElement transactionCreateDescriptionInput;

    @FindBy(css = "button[data-test='transaction-create-submit-request']")
    @CacheLookup
    WebElement transactionCreateSubmitRequest;

    @FindBy(css = "button[data-test='transaction-create-submit-payment']")
    @CacheLookup
    WebElement transactionCreateSubmitPayment;

    @FindBy(css = "span[data-test*='transaction-sender-']")
    @CacheLookup
    List<WebElement> transactionSenders;

    @FindBy(css = "span[data-test*='transaction-action-']")
    @CacheLookup
    List<WebElement> transactionActions;

    @FindBy(css = "span[data-test*='transaction-receiver-']")
    @CacheLookup
    List<WebElement> transactionReceiver;

    @FindBy(css = "li[data-test*='transaction-item-']")
    @CacheLookup
    List<WebElement> transactionItem;

    @FindBy(css = "li[data-test*='user-list-item-'")
    @CacheLookup
    List<WebElement> persons;

    @FindBy(css = "a[data-test='sidenav-home']")
    @CacheLookup
    WebElement homeLink;

    public BankPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BankPage openFilter() {
        click(transactionFilter);

        return this;
    }

    public BankPage clickAccountBalance() {
        Actions build = new Actions(driver);
        build.moveToElement(accountBalance).click().build().perform();

        return this;
    }


    public LoginPage logout() {
        click(logoutButton);

        return new LoginPage(driver);
    }

    public PairOfIntegers changeValueOnASlider(double percentage) {

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        Rectangle rectangle = null;
        By byItem = By.cssSelector("span[data-test='transaction-list-filter-amount-range-slider']");

        WebElement element = BaseTest.findNotStaleElement(driver, byItem);
        rectangle = element.getRect();

        int x = rectangle.x;
        int y = rectangle.y + (rectangle.height / 2);

        int newX = (int)(x + rectangle.width * percentage);
        int newY = y;

        Actions actions = new Actions(driver).moveToLocation(newX, newY).click();
        actions.perform();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value = js.executeScript("return document.querySelector('input[type=\"hidden\"]').value").toString();

        log.debug("Value on a slider: "+value);

        return new PairOfIntegers(value);
    }

    public List<Double> getValuesOfTransactions() {
        List<WebElement> elements = driver.findElements(By.cssSelector("span[data-test*='transaction-amount-']"));
        List<Double> values = elements.stream()
                .map(el -> el.getText())
                .map( text -> {
                    text = text.replace('$', ' ');
                    text = text.replace('+', ' ');
                    text = text.replace('-', ' ');
                    log.debug("  : " +text);
                    return Double.parseDouble(text);
                })
                .collect(Collectors.toList());

        return values;
    }

    public void clickMineTransactions() {
        clickWithWait(mineTransactions,10);
    }

    public void clickNewTransaction() {
        click(newTransaction);
    }

    public void clickPersonWithName(String userTransaction) {
        int itemIndex = 0;
        for (WebElement el: persons) {
            String text = el.getText();
            String[] values = text.split("\n");
            log.debug("values[0] "+values[0] + " userTransaction "+userTransaction);
            if(values[0].equals(userTransaction)) {

                break;
            }
            itemIndex++;
        }

        click(persons.get(itemIndex));

    }

    public NewTransactionPage fillTransactionData(Transaction transaction1) {
        type(amountInput, transaction1.amount + "");
        type(transactionCreateDescriptionInput, transaction1.comment);

        if(transaction1.request) {
            click(transactionCreateSubmitRequest);
        }
        else  {
            click(transactionCreateSubmitPayment);
        }

        return new NewTransactionPage(driver);
    }

    public int findTransactionOnMineTransactionList(User user, Transaction transaction, String userTransaction) {
        // transaction-sender--lHs6ZbxO
        AtomicInteger index = new AtomicInteger(-1);
        int numberOfSpecificTransactions = transactionReceiver.stream()
                .filter(el -> (el.getText().equals(userTransaction) && index.getAndIncrement() > -2
                && checkAction(index.get(), transactionActions, transaction.isRequest())
                && checkSender(index.get(), transactionSenders,user.firstName, user.lastName)) )
                .mapToInt(x -> 1)
                .sum();

        return numberOfSpecificTransactions;
    }

    private boolean checkAction(int index, List<WebElement> elementHandlesAction, boolean request) {
        String action = "paid";
        if(request) {
            action = "requested";
        }

        return checkIfElementIsPresentAtTheSamePosition(index, elementHandlesAction, action);
    }

    private boolean checkSender(int index, List<WebElement> elementHandlesSender, String firstName, String lastName) {
        String user = firstName + " " + lastName;

        return checkIfElementIsPresentAtTheSamePosition(index, elementHandlesSender, user);
    }

    private boolean checkIfElementIsPresentAtTheSamePosition(int index, List<WebElement> elementHandles, String value) {

        int i = 0;
        for(WebElement elementHandleSender : elementHandles) {
            if(i == index &&  elementHandleSender.getText().equals(value)) {
                return true;
            }
            i++;
        }

        return false;
    }

    public TransactionDetailPage acceptNthTransactions(int index) {
        click(transactionItem.get(index));

        return new TransactionDetailPage(driver);
    }

    public int getTransactionsItemSize() {
        return transactionItem.size();
    }

    public void clickHome() {
        click(homeLink);
    }
}
