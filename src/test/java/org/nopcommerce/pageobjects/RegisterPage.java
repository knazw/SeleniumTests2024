package org.nopcommerce.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pageobjects.ExtendedBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends ExtendedBasePage {

    @FindBy(xpath = "//*[@type='radio' and @id='gender-male']")
    @CacheLookup
    WebElement radioMale;

    @FindBy(xpath = "//*[@type='radio' and @id='gender-female']")
    @CacheLookup
    WebElement radioFemale;

    @FindBy(id = "FirstName")
    @CacheLookup
    WebElement inputFirstName;

    @FindBy(id = "LastName")
    @CacheLookup
    WebElement inputLastName;

    @FindBy(css = "select[name='DateOfBirthDay']")
    @CacheLookup
    WebElement selectDateOfBirthDay;

    @FindBy(css = "select[name='DateOfBirthMonth']")
    @CacheLookup
    WebElement selectDateOfBirthMonth;

    // DateOfBirthYear
    @FindBy(css = "select[name='DateOfBirthYear']")
    @CacheLookup
    WebElement selectDateOfBirthYear;

    @FindBy(id = "Email")
    @CacheLookup
    WebElement inputEmail;

    @FindBy(id = "Password")
    @CacheLookup
    WebElement inputPassword;
    @FindBy(id = "ConfirmPassword")
    @CacheLookup
    WebElement inputConfirmPassword;

    @FindBy(id = "Newsletter")
    @CacheLookup
    WebElement checkboxNewsletter;

    //
    @FindBy(id = "register-button")
    @CacheLookup
    WebElement buttonRegister;

    /*
    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    */
    public RegisterPage(WebDriverManager webDriverManager) {
        super(webDriverManager);
        PageFactory.initElements(driver, this);
    }

    public RegisterPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://demo.nopcommerce.com/register?returnUrl=%2F");
    }

    public void selectMaleOrFemale(String gender) {
        if(gender.equals("female")) {
            click(radioFemale);
        }
        else if (gender.equals("male")) {
            click(radioMale);
        }
    }

    public void setDayOfBirth(int day) {
        type(selectDateOfBirthDay, day + "");
    }

    public void setMonthOfBirth(String monthOfBirth) {
        type(selectDateOfBirthMonth, monthOfBirth);
    }

    public void setYearOfBirth(int yearOfBirth) {
        type(selectDateOfBirthYear, yearOfBirth + "");
    }

    public void setEmail(String email) {
        type(inputEmail, email);
    }

    public void setFirstName(String firstName) {
        type(inputFirstName, firstName);
    }

    public void setLastName(String lastNameValue) {
        type(inputLastName, lastNameValue);
    }

    public void setInputPassword(String password) {
        type(inputPassword, password);
    }

    public void setInputConfirmPassword(String password) {
        type(inputConfirmPassword, password);
    }

    public void setCheckboxNewsletter(boolean newsletter) {
        if(!newsletter)
            checkboxNewsletter.click();
    }

    public RegisterResult clickRegister() {
        click(buttonRegister);

        return new RegisterResult(webDriverManager);
        //return new RegisterResult(driver);
    }
}
