package org.nopcommerce.pageobjects;

import org.example.pageobjects.ExtendedBasePage;
import org.nopcommerce.pagefragments.HeaderUpper;
import org.nopcommerce.pagefragments.MenuItems;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class PaymentMethodPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {

    static final Logger log = getLogger(lookup().lookupClass());


    @FindBy(xpath = "//input[@id='paymentmethod_1']")
    @CacheLookup
    WebElement creditCard;

    @FindBy(xpath = "//button[@class='button-1 payment-method-next-step-button']")
    @CacheLookup
    WebElement continueButton;

    public PaymentMethodPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public PaymentMethodPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public PaymentMethodPage clickCreditCard() {
        clickWithWait(creditCard, 10);
        log.debug("clickCreditCard previously was failing");

        return this;
    }

    public PaymentInfoPage clickContinueButton() {
        click(continueButton);

        return new PaymentInfoPage(driver);
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
