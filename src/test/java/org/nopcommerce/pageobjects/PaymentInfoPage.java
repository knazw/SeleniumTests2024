package org.nopcommerce.pageobjects;

import org.example.pageobjects.ExtendedBasePage;
import org.nopcommerce.pagefragments.HeaderUpper;
import org.nopcommerce.pagefragments.MenuItems;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class PaymentInfoPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(xpath = "//input[@id='CardholderName']")
    @CacheLookup
    WebElement cardholderName;

    @FindBy(xpath = "//input[@id='CardNumber']")
    @CacheLookup
    WebElement cardNumber;

    @FindBy(xpath = "//select[@id='ExpireMonth']")
    @CacheLookup
    WebElement cardExpirationMonth;

    @FindBy(xpath = "//input[@id='CardCode']")
    @CacheLookup
    WebElement cardCode;

    @FindBy(xpath = "//button[@class='button-1 payment-info-next-step-button']")
    @CacheLookup
    WebElement continueButton;


    public PaymentInfoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public PaymentInfoPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public PaymentInfoPage fillCardholderName(String name) {
        By byItem = getByFromElement(cardholderName.toString());
        WebElement foundCardholderName = findWithWait(byItem, 10);

        type(foundCardholderName, name);

//        type(cardholderName, name);

        return this;
    }

    public PaymentInfoPage fillCardNumber(String number) {
        type(cardNumber, number);

        return this;
    }

    public PaymentInfoPage fillExpirationMonth(String month) {
        Select select = new Select(cardExpirationMonth);
        select.selectByVisibleText(month);

        return this;
    }

    public PaymentInfoPage fillCardCode(String code) {
        type(cardCode, code);

        return this;
    }


    public ConfirmOrderPage clickContinueButton() {
        click(continueButton);

        return new ConfirmOrderPage(driver);
    }




    @Override
    public HeaderUpper getHeaderUpperPage() {
        return new HeaderUpper(driver);
    }

    @Override
    public MenuItems getMenuItemsFragment() {
        return new MenuItems(driver);
    }
}
