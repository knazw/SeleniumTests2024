package org.nopcommerce.pagefragments;

import org.example.pageobjects.ExtendedBasePage;
import org.nopcommerce.pageobjects.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class CartFloatingWindow extends ExtendedBasePage {
    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(xpath = "//button[@class='button-1 cart-button']")
    @CacheLookup
    WebElement cart;


    public CartFloatingWindow(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CartFloatingWindow(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public CartPage clickCart() {
        clickWithWait(cart, 10);

        return new CartPage(driver);
    }
}
