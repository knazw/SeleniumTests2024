package org.example.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    public WebDriver getDriver() {
        return driver;
    }

    protected WebDriver driver;
    protected WebDriverWait wait;
    int timeoutSec = 5;

    public ExtendedBasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public ExtendedBasePage(String browser) {
        log.debug("ExtendedBasePage {}", browser);

        driver = WebDriverManager.getInstance(browser).create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        log.debug("page load timeout: "+driver.manage().timeouts().getPageLoadTimeout().toString());
        /*
        try {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.emulateNetworkConditions(
                    false,
                    0,
                    16 * 1024,
                    64 * 1024,
                    Optional.of(ConnectionType.CELLULAR2G)
            ));
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            driver.quit();
        }
        */

        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
    }

    public void quit() {
        if(driver != null) {
            driver.quit();
        }
    }

    public void visit(String url) {
        driver.get(url);

        log.debug("page load timeout: "+driver.manage().timeouts().getPageLoadTimeout().toString());
        /*
        try {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.emulateNetworkConditions(
                    false,
                    0,
                    8 * 1024,
                    64 * 1024,
                    Optional.of(ConnectionType.CELLULAR2G)
            ));
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            driver.quit();
        }
        */
    }
    public WebElement find(By element) {
        return driver.findElement(element);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void clickWithWaits(WebElement element) {

        By byElement = getByFromElement(element);

        new WebDriverWait(driver, Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(250))
                .until(ExpectedConditions.elementToBeClickable(byElement));

        if(element.getRect().width < 1 || element.getRect().height < 1) {
            log.debug("Size == 0, waiting ...");

            waitUntileSizeOfElementWillBeProper(byElement);
        }
        element.click();
    }

    public void click(By element) {
        click(find(element));
    }

    public void type(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void type(By element, String text) {
        type(find(element), text);
    }

    public void moveToElemenent(WebElement element) {
        Actions action = new Actions(driver);

        action.moveToElement(element);
        action.perform();
    }

    protected void waitUntileSizeOfElementWillBeProper(By byItem) {

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        wait.until(new ExpectedCondition<Boolean>(){
            @Override
            public Boolean apply(WebDriver d) {
                try {

                    log.debug("size1:"+d.findElement(byItem).getSize().width);
                    log.debug("size2:"+d.findElement(byItem).getSize().height);
                    log.debug("================");
                    return d.findElement(byItem).getSize().width > 0 &&
                            d.findElement(byItem).getSize().height > 0;
                }
                catch (Exception ex) {
                    log.error(ex.toString());
                }
                return false;
            }});
    }

    protected By getByFromElement(WebElement element) {

        By by;
        log.debug("element.toString().split(\"->\"): "+element.toString());
        String[] selectorWithValue= (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");

        String selector = selectorWithValue[0].trim();
        String value = selectorWithValue[1].trim();

        log.debug("Selector in getByFromElement: "+selector + ", value "+value);
        switch (selector) {
            case "id":
                by = By.id(value);
                break;
            case "className":
                by = By.className(value);
                break;
            case "tagName":
                by = By.tagName(value);
                break;
            case "xpath":
                by = By.xpath(value);
                break;
            case "cssSelector":
                by = By.cssSelector(value);
                break;
            case "linkText":
                by = By.linkText(value);
                break;
            case "name":
                by = By.name(value);
                break;
            case "partialLinkText":
                by = By.partialLinkText(value);
                break;
            case "css selector":
                by = By.cssSelector(value);
                break;
            default:
                throw new IllegalStateException("locator : " + selector + " not found!!!");
        }
        return by;
    }
}
