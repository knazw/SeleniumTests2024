package org.nopcommerce.pageobjects;

import org.example.pageobjects.ExtendedBasePage;
import org.nopcommerce.pagefragments.HeaderUpper;
import org.nopcommerce.pagefragments.MenuItems;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class MainPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {
    // https://demo.nopcommerce.com/

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(css = "a[class='ico-register']")
    @CacheLookup
    WebElement iconRegister;

    @FindBy(css = "a[class='ico-login']")
    @CacheLookup
    WebElement iconLogin;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public RegisterPage clickRegister() {
        click(iconRegister);

        return new RegisterPage(driver);
    }

    public LoginPage clickLogin() {
        clickWithWaits(iconLogin);

        return new LoginPage(driver);
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
