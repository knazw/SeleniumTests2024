package org.nopcommerce.pageobjects;

import org.example.pageobjects.ExtendedBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class ShippingDetailsPage extends ExtendedBasePage {

    @FindBy(id = "CountryId")
    @CacheLookup
    WebElement country;

    @FindBy(id = "ZipPostalCode")
    @CacheLookup
    WebElement postalCode;

    @FindBy(xpath = "//div[@class='estimate-shipping-row-item shipping-item'][position()=1]")
    @CacheLookup
    List<WebElement> shippingMethodName;

    @FindBy(xpath = "//button[@class='button-2 apply-shipping-button']")
    @CacheLookup
    WebElement buttonApply;

    public ShippingDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ShippingDetailsPage selectCountry(String country) {
        By byCountry = getByFromElement(this.country);
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(byCountry));


        Select select = new Select(this.country);
        select.selectByVisibleText(country);

        return this;
    }

    public ShippingDetailsPage setPostalCode(String postalCode) {
        type(this.postalCode, postalCode);

        return this;
    }

    public ShippingDetailsPage checkRadioButtonShippingMethod(String shippingMethodName) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class);

        WebElement choosenMethod = this.shippingMethodName.stream()
                .filter(el -> el.getText().equals(shippingMethodName))
                .findFirst().orElse(null);

        WebElement element = fluentWait.until(ExpectedConditions.elementToBeClickable(choosenMethod));
        WebElement radioButton = driver.findElement(with(By.tagName("label"))
                .toLeftOf(element));
        radioButton.click();

        return this;
    }

    public MainPage applyShippingMethod() {
        clickWithWaits(buttonApply);

        return new MainPage(driver);
    }


}
