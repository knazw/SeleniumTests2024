package org.nopcommerce.pagefragments;

import org.example.pageobjects.ExtendedBasePage;
import org.nopcommerce.pageobjects.MyAccountPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class HeaderUpper extends ExtendedBasePage {
    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(xpath = "//a[@class='ico-account']")
    @CacheLookup
    WebElement myAccountLink;

    @FindBy(xpath = "//span[@class='cart-label']")
    @CacheLookup
    WebElement cart;

    public HeaderUpper(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public HeaderUpper(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public MyAccountPage clickMyAccount() {
        click(myAccountLink);

        return new MyAccountPage(driver);
    }

    public HeaderUpper mouseOverCartLabel() {
        moveToElemenent(cart);

        return this;
    }

}
