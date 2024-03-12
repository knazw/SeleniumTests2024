package org.nopcommerce.pageobjects;

import org.example.pageobjects.ExtendedBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class MyAccountPage extends ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    @FindBy(id = "FirstName")
    @CacheLookup
    WebElement firstName;

    @FindBy(id = "LastName")
    @CacheLookup
    WebElement lastName;

    @FindBy(id = "Email")
    @CacheLookup
    WebElement email;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MyAccountPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/");
    }

    public String getFirstName() {
        return firstName.getAttribute("value");
    }

    public String getLastName() {
        return lastName.getAttribute("value");
    }

    public String getEmail() {
        return email.getAttribute("value");
    }


}
