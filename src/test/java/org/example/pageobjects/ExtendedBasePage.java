package org.example.pageobjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.utilities.PropertiesStorage;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverManager getWebDriverManager() {
        return webDriverManager;
    }

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected WebDriverManager webDriverManager;
    int timeoutSec = 5;

    public ExtendedBasePage(WebDriverManager webDriverManagerParam) {
        this.webDriverManager = webDriverManagerParam;
        this.driver = this.webDriverManager.getWebDriver();
    }

    /*
    public ExtendedBasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }
    */

    public ExtendedBasePage(String browser) {
        log.debug("ExtendedBasePage {}", browser);

        driver = createDriver(browser);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        log.debug("page load timeout: "+driver.manage().timeouts().getPageLoadTimeout().toString());

        /*
        try {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.emulateNetworkConditions(
                    false,
                    0,
                    64 * 1024,
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

    protected WebDriver createDriver(String browser) {
        webDriverManager = getBrowserInstance(browser);
        webDriverManager = addRemoteAddress(webDriverManager, PropertiesStorage.getInstance().getRemoteAddress());
        webDriverManager = addInstanceInDocker(webDriverManager, PropertiesStorage.getInstance().getInstanceInDocker());
        webDriverManager = addVnc(webDriverManager, PropertiesStorage.getInstance().getEnableVnc());
        webDriverManager = addFramerate(webDriverManager, PropertiesStorage.getInstance().getFramerate());
        webDriverManager = addEnableRecording(webDriverManager, PropertiesStorage.getInstance().getEnableRecording());

        return webDriverManager.create();
    }

    private WebDriverManager addEnableRecording(WebDriverManager webDriverManager, boolean enableRecording) {
        if(!enableRecording) {
            return webDriverManager;
        }
        return webDriverManager.enableRecording();
    }

    private WebDriverManager addFramerate(WebDriverManager webDriverManager, int framerate) {
        if(framerate <= 0) {
            return webDriverManager;
        }
        return webDriverManager.dockerRecordingFrameRate(framerate);
    }

    private WebDriverManager addVnc(WebDriverManager webDriverManager, boolean enableVnc) {
        if(!enableVnc) {
            return webDriverManager;
        }
        return webDriverManager.enableVnc();
    }

    private WebDriverManager addInstanceInDocker(WebDriverManager webDriverManager, boolean instanceInDocker) {
        if(!instanceInDocker) {
            return webDriverManager;
        }
        return webDriverManager.browserInDocker();
    }

    private WebDriverManager addRemoteAddress(WebDriverManager webDriverManager, String remoteAddress) {
        if(remoteAddress == null) {
            return webDriverManager;
        }
        return webDriverManager.remoteAddress(remoteAddress);
    }

    private WebDriverManager getBrowserInstance(String browser) {
        WebDriverManager webDriverManager = null;
        switch (browser) {
            case "chrome":
            case "edge":
            case "firefox": {
                webDriverManager = WebDriverManager.getInstance(browser);

                break;
            }
            case "chromeheadless" : {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");

                webDriverManager = WebDriverManager.getInstance("chrome").capabilities(options);
                break;
            }
            case "firefoxheadless" : {

                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless");

                webDriverManager = WebDriverManager.getInstance("firefox").capabilities(options);
                break;
            }
            case "edgeheadless" : {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless=new");
                webDriverManager = WebDriverManager.getInstance("edge").capabilities(options);
                break;
            }
            default: {
                webDriverManager = WebDriverManager.getInstance("chrome");
            }
        }
        return webDriverManager;
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
    }

    public void quit() {
        if(webDriverManager != null) {
            webDriverManager.stopDockerRecording();
            webDriverManager.quit();
        }
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
                    64 * 1024,
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

    public WebElement findWithWait(By byItem, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));

        return wait.until(ExpectedConditions.elementToBeClickable(byItem));
    }
    public void click(WebElement element) {
        element.click();
    }

    public void clickWithWait(WebElement element, long seconds) {
        By byItem = getByFromElement(element.toString());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));

        WebElement foundElement = wait.until(ExpectedConditions.elementToBeClickable(byItem));
        click(foundElement);
    }

    public void clickWithWaits(WebElement element) {

        By byElement = getByFromElement(element.toString());

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(250));
        WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(byElement));

        if(elem.getRect().width < 1 || elem.getRect().height < 1) {
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
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();

        if(cap.getBrowserName().startsWith("firefox")) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }

        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public void waitUntilElementWillBeNotDisplayed(WebElement element) {
        boolean value = new WebDriverWait(driver, Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .until(ExpectedConditions.invisibilityOf(element));
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

    protected By getByFromElement(String elementString) {

        log.debug("elementString.toString().split(\"->\"): "+elementString);
        if(elementString.contains("->")) {
            String[] selectorWithValue = (elementString.split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");

            String selector = selectorWithValue[0].trim();
            String value = selectorWithValue[1].trim();

            log.debug("Selector in getByFromElement: " + selector + ", value " + value);
            return getBy(selector, value);
        }
        else {
            return getByFromNotInitializedElement(elementString);
        }
    }

    private static By getBy(String selector, String value) {
        By by;
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

    protected By getByFromNotInitializedElement(String element) {
        String[] array = element.split("DefaultElementLocator '");
        if(array.length != 2) {
            return null;
        }
        array = array[1].split(":");

        if(array.length != 2) {
            return null;
        }

        String selector = array[0];

        if(selector.contains(".")) {
            String[] selectors = selector.split("\\.");
            selector = selectors[1];
        }

        String value = array[1].trim();
        value = value.substring(0,value.length() - 1);

        return  getBy(selector, value);
    }
}
