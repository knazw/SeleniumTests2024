package org.nopcommerce.pageobjects;

import org.example.pageobjects.ExtendedBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.openqa.selenium.support.locators.RelativeLocator.with;
import static org.slf4j.LoggerFactory.getLogger;

public class ShippingDetailsPage extends ExtendedBasePage {
    static final Logger log = getLogger(lookup().lookupClass());

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
    
    @FindBy(xpath = "//div[@class='estimate-shipping-row-item shipping-item'][contains(text(),'2nd')]")
    @CacheLookup
    WebElement shippingBox;

    public ShippingDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ShippingDetailsPage selectCountry(String country) {
        By byCountry = getByFromElement(this.country.toString());
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(byCountry));

        Select select = new Select(this.country);
        select.selectByVisibleText(country);

        return this;
    }

    public ShippingDetailsPage setPostalCode(String postalCode) {
        type(this.postalCode, postalCode);

        return this;
    }

    public ShippingDetailsPage shippingMethodAvailable() {
//        takeSnapShot(driver, "C:\\src\\selenium\\_screenshots\\shippingMethodAvailable-start.png");
        clickWithWait(shippingBox,10);

        return this;

    }

    public ShippingDetailsPage checkRadioButtonShippingMethod(String shippingMethodName) {
        // wait until whole item is displayed
        By byShippingBox = getByFromElement(shippingBox.toString());
        findWithWait(byShippingBox, 10);

        // find reference to specific item
        WebElement choosenMethod = this.shippingMethodName.stream()
                .filter(el -> el.getText().equals(shippingMethodName))
                .findFirst().orElse(null);

        WebElement radioButton = driver.findElement(with(By.tagName("label"))
                .toLeftOf(choosenMethod));
        radioButton.click();

        return this;
    }

    public MainPage applyShippingMethod() {
        clickWithWaits(buttonApply);

        By byItem = By.xpath("//div[@class='mfp-content']");
        WebElement popupWhichShouldDissapear = driver.findElement(byItem);

        waitUntilElementWillBeNotDisplayed(popupWhichShouldDissapear);

        return new MainPage(driver);
    }


}
