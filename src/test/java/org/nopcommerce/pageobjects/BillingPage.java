package org.nopcommerce.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pageobjects.ExtendedBasePage;
import org.nopcommerce.pagefragments.HeaderUpper;
import org.nopcommerce.pagefragments.MenuItems;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BillingPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {

    static final Logger log = getLogger(lookup().lookupClass());


    @FindBy(xpath = "//select[@id='BillingNewAddress_CountryId']")
    @CacheLookup
    WebElement country;

    @FindBy(xpath = "//input[@id='BillingNewAddress_City']")
    @CacheLookup
    WebElement city;

    @FindBy(xpath = "//input[@id='BillingNewAddress_Address1']")
    @CacheLookup
    WebElement address1;

    @FindBy(xpath = "//input[@id='BillingNewAddress_ZipPostalCode']")
    @CacheLookup
    WebElement postalCode;

    @FindBy(xpath = "//input[@id='BillingNewAddress_PhoneNumber']")
    @CacheLookup
    WebElement phoneNumber;

    @FindBy(xpath = "//button[@class='button-1 new-address-next-step-button']")
    @CacheLookup
    WebElement continueButton;

    /*
    public BillingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public BillingPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public BillingPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public BillingPage setCountry(String countryName) {
        Select select = new Select(country);
        select.selectByVisibleText(countryName);

        return this;
    }

    public BillingPage setCity(String cityName) {
        By byItem = getByFromElement(city.toString());
        WebElement foundCity = findWithWait(byItem, 10);
        type(foundCity, cityName);

        return this;
    }

    public BillingPage setAddress1(String address1Value) {
        type(address1, address1Value);

        return this;
    }

    public BillingPage postalCode(String postalCodeValue) {
        type(postalCode, postalCodeValue);

        return this;
    }

    public BillingPage phoneNumber(String phoneNumberValue) {
        type(phoneNumber, phoneNumberValue);

        return this;
    }

    public ShippingMethodPage clickContinueButton(){
        click(continueButton);

        return new ShippingMethodPage(webDriverManager);
        //return new ShippingMethodPage(driver);
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

    public BillingPage waitUntilBillingAddressIsInteractable() {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(ElementNotInteractableException.class);

        By byCity = getByFromElement(city.toString());

        fluentWait.until(ExpectedConditions.elementToBeClickable(byCity));

        return this;
    }
}
