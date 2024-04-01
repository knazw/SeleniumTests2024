package org.nopcommerce.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
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

public class CartPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(xpath = "//input[@id='termsofservice']")
    @CacheLookup
    WebElement agreement;

    @FindBy(xpath = "//button[@id='checkout']")
    @CacheLookup
    WebElement checkout;

    /*
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public CartPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public CartPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    @Override
    public HeaderUpper getHeaderUpperPage() {

        return new HeaderUpper(webDriverManager);
//        return new HeaderUpper(driver);
    }

    @Override
    public MenuItems getMenuItemsFragment() {

        return new MenuItems(webDriverManager);
//        return new MenuItems(driver);
    }

    public CartPage clickAgreement() {
        click(agreement);

        return this;
    }

    public BillingPage clickCheckout() {
        click(checkout);

        return new BillingPage(webDriverManager);
        //return new BillingPage(driver);
    }
}
