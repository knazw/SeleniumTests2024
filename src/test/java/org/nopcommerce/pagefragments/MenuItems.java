package org.nopcommerce.pagefragments;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pageobjects.ExtendedBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class MenuItems extends ExtendedBasePage {
    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(linkText = "Apparel")
    @CacheLookup
    WebElement linkApparel;


    /*
    public MenuItems(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public MenuItems(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public MenuItems(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public Products clickApparel() {
        click(linkApparel);

        return new Products(webDriverManager);
    }
}
