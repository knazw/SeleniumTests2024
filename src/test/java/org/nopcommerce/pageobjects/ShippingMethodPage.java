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

public class ShippingMethodPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(xpath = "//input[@id='shippingoption_2']")
    @CacheLookup
    WebElement shippingMethod;

    @FindBy(xpath = "//button[@class='button-1 shipping-method-next-step-button']")
    @CacheLookup
    WebElement continueButton;

    public ShippingMethodPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ShippingMethodPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public ShippingMethodPage clickShippingMethod() {
        click(shippingMethod);

        return this;
    }

    public PaymentMethodPage clickContinueButton() {
        click(continueButton);

        return new PaymentMethodPage(driver);
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
