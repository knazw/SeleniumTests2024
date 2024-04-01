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

public class OrderDetailsPage extends ExtendedBasePage implements IHasHeaderUpper, IHasMenuItems  {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(xpath = "//li[@class='email']")
    @CacheLookup
    WebElement email;

    @FindBy(xpath = "//div[@class='attributes']")
    @CacheLookup
    WebElement attributes;

    /*
    public OrderDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
     */
    public OrderDetailsPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public OrderDetailsPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public String getEmail() {
        String text = email.getText();

        String[] array = text.split(":");
        if(array.length > 1) {
            return array[1].replaceAll(" " , "");
        }
        return null;
    }

    public String getColor() {
        String text = attributes.getText();

        String[] array = text.split("\n");
        if(array.length > 2) {
            array = array[1].split(":");
            if (array.length > 1) {
                return array[1].replaceAll(" " , "");
            }
        }

        return null;
    }

    @Override
    public HeaderUpper getHeaderUpperPage() {

        return new HeaderUpper(webDriverManager);
        //return new HeaderUpper(driver);
    }

    @Override
    public MenuItems getMenuItemsFragment() {

        return new MenuItems(webDriverManager);
        //return new MenuItems(driver);
    }
}
