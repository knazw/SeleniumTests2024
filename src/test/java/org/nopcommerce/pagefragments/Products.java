package org.nopcommerce.pagefragments;

import org.example.pageobjects.ExtendedBasePage;
import org.nopcommerce.pageobjects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class Products extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems , IHasCartFloatingWindow, IHasAddedToCartPopup {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(css = "h2[class='product-title']")
    @CacheLookup
    List<WebElement> productsTitles;

    @FindBy(id = "product_attribute_6")
    @CacheLookup
    WebElement selectSize;

    @FindBy(id = "product_attribute_7")
    @CacheLookup
    WebElement selectColor;

    @FindBy(css = "label[for*='product_attribute_8_'")
    @CacheLookup
    List<WebElement> selectStyle;


    @FindBy(css = "div[class='tooltip-header']")
    @CacheLookup
    List<WebElement> tooltipHeaders;

    @FindBy(id = "open-estimate-shipping-popup-24")
    @CacheLookup
    WebElement shippingDetails;

    @FindBy(xpath = "//div[@class='shipping-address']/span")
    @CacheLookup
    WebElement shippingAddress;

    @FindBy(xpath = "//button[@class='button-1 add-to-cart-button']")
    @CacheLookup
    WebElement addToCart;

    public Products(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public Products(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public Products select() {
        WebElement el = driver.findElement(By.linkText("Shoes"));
        el.click();

        return new Products(driver);
    }

    public Products selectNthProductOnPage(int productNumber) {
        click(productsTitles.get(productNumber));

        return new Products(driver);
    }

    public Products selectSize(int size) {
        Select select = new Select(selectSize);
        String optionLabel = "" + size;
        select.selectByVisibleText(optionLabel);

        return this;
    }

    public Products selectColor(String color) {
        Select select = new Select(selectColor);
        select.selectByVisibleText(color);

        return this;
    }

    public Products moveToStyle(int position) {
        moveToElemenent(selectStyle.get(position));

        return new Products(driver);
    }


    public Products clickStyle(int position) {
        log.debug("clickStyle "+position);
        click(selectStyle.get(position));

        return this;
    }

    public String getStyleTooltip() {
        WebElement element = driver.findElement(By.cssSelector("div[class='tooltip-header']"));
        element.isDisplayed();

        return tooltipHeaders.stream()
                .filter(el -> el.isDisplayed())
                .findFirst()
                .map(el -> el.getText()).orElse("");
    }

    public Products moveToOtherPartOfAScreen() {
//        moveToElemenent(selectColor);

        Actions actions = new Actions(driver);
        actions.moveToLocation(0,0).build().perform();

        return this;
    }


    public ShippingDetailsPage openShippingDetails() {
        clickWithWaits(shippingDetails);

        return new ShippingDetailsPage(driver);
    }


    public String getShippingAddress() {
        By byItem = getByFromElement(shippingAddress.toString());
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class)
                .until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        WebElement webElement = d.findElement(byItem);
                        if(webElement.getText() != null && webElement.getText().length() > 0) {
                            return true;
                        }


                        return false;
                    }
                });


        return shippingAddress.getText();
    }


    @Override
    public HeaderUpper getHeaderUpperPage() {
        return new HeaderUpper(driver);
    }

    @Override
    public MenuItems getMenuItemsFragment() {
        return new MenuItems(driver);
    }

    public Products addToCart() {
        click(addToCart);

        return this;
    }

    @Override
    public CartFloatingWindow getCartFloatingWindow() {
        return new CartFloatingWindow(driver);
    }

    @Override
    public AddedToCartPopup getAddedToCartPopup() {
        return new AddedToCartPopup(driver);
    }
}
