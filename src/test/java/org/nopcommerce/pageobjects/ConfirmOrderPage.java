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

public class ConfirmOrderPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(xpath = "//button[@class='button-1 confirm-order-next-step-button']")
    @CacheLookup
    WebElement confirmOrder;

    /*
    public ConfirmOrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public ConfirmOrderPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public ConfirmOrderPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public CheckoutCompletedPage clickConfirmOrder() {
        clickWithWait(confirmOrder, 10);

        return new CheckoutCompletedPage(webDriverManager);
        //return new CheckoutCompletedPage(driver);
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


}
