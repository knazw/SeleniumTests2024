package org.nopcommerce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.nopcommerce.helpers.FileOperations;
import org.nopcommerce.model.Product;
import org.nopcommerce.model.ShippingMethod;
import org.nopcommerce.model.User;
import org.nopcommerce.pageobjects.MainPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.utilities.BaseTestHelpers;
import org.utilities.PropertiesStorage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.utilities.BaseTestHelpers.getBrowser;

public class BaseTest {

    static final Logger log = getLogger(lookup().lookupClass());
    MainPage mainPage;

    @BeforeAll
    protected static void setupBeforeAll() {
//        BaseTest.class.getClassLoader().getResourceAsStream("application.properties");

        Properties properties = new Properties();
        try (InputStream is = BaseTest.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);

            PropertiesStorage propertiesStorage = PropertiesStorage.getInstance("");
            propertiesStorage.setProperties(properties);
        }
        catch (IOException ioException) {
            log.error(ioException.toString());
        }

    }

    @BeforeEach
    void setup(TestInfo info) {
        String browserName = getBrowser(info.getDisplayName());

        if(browserName == null) {
            mainPage = new MainPage("chrome");
        }
        else {
            mainPage = new MainPage(browserName);
        }
        mainPage.getDriver().manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if(mainPage != null) {
            mainPage.quit();
        }
    }

    protected void makeScreenshotAfterFail(String name) {
        String fileName = BaseTestHelpers.createFileNameFromCurrentTime(name+"-failed",null);

        BaseTestHelpers.takeSnapShot(mainPage.getDriver(), "C:\\src\\selenium\\_screenshots\\"+fileName+".png");
    }

    @RegisterExtension
    AfterTestExecutionCallback afterTestExecutionCallback = new AfterTestExecutionCallback() {
        @Override
        public void afterTestExecution(ExtensionContext context) throws Exception {
            Optional<Throwable> exception = context.getExecutionException();
            if (exception.isPresent()) { // has exception
                makeScreenshotAfterFail(context.getTestMethod().get().getName());
            } else {                     // no exception
            }
        }
    };

    public static WebElement findNotStaleElement(WebDriver driver, By byItem) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(Duration.ofSeconds(1))
                .until((WebDriver d) -> {
                    WebElement element = d.findElement(byItem);
                    try {
                        Rectangle rect = element.getRect();
                    }
                    catch (StaleElementReferenceException staleElementReferenceException) {
                        log.error(staleElementReferenceException.toString());
                        throw staleElementReferenceException;
                    }
                    return element;
                });
    }

    public static Rectangle findNotStaleRectangle(WebDriver driver, By byItem) {

        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(Duration.ofSeconds(1))
                .until((WebDriver d) -> {
                    WebElement element = d.findElement(byItem);
                    Rectangle rect = null;

                    try {
                        rect = element.getRect();
                    }
                    catch (StaleElementReferenceException staleElementReferenceException) {
                        log.error(staleElementReferenceException.toString());
                        throw staleElementReferenceException;
                    }
                    return rect;
                });

    }


    public static boolean performActionOnWebElement(WebDriver driver,
                                                    WebElement element, By byItem, Consumer<WebElement> action, int maxQuantity) {
        boolean result = false;
        int counter = 0;
        while(counter++ < maxQuantity) {
            try {
                action.accept(element);
                result = true;

                break;
            }
            catch (StaleElementReferenceException staleElementReferenceException) {
                element = driver.findElement(byItem);
            }
        }

        return result;
    }


    protected synchronized static User createUser() {

        String idString = FileOperations.readFromFile("user.json");
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        objectMapper.registerModule(new JSR310Module());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        User previousUser;
        try {
            previousUser = objectMapper.readValue(idString, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        User user = new User();
        user.dateOfBirth = LocalDate.of(1980,5,27);

        user.firstName = "Adam";
        user.lastName = "Nowak";
        user.password = "123456";
        user.gender = "male";
        user.companyName = "Intel";
        user.email = changeEmail(previousUser.email);
        user.city = "Miasteczko";
        user.address = "Adresowa 1/2";
        user.creditCard = "4111111111111111";
        user.verificationCode = "234";
        user.expirationOfCreditCard = "06/24";
        user.postalCode = "34567";
        user.country = "Poland";
        user.phoneNumber = "89274234";

        FileOperations.writeToFile(user);

        return user;
    }

    protected static Product createProduct() {
        Product product = new Product();
        product.size = 8;
        product.color = "White/Black";

        return product;
    }


    private static String changeEmail(String email) {
        String[] split = email.split("@");
        return (Integer.parseInt(split[0]) + 1) + "@" + split[1];
    }

    protected static ShippingMethod createShippingMethod() {
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.shippingMethod = "2nd Day Air";
        shippingMethod.country = "Poland";
        shippingMethod.postalCode = "823782";

        return shippingMethod;
    }
}
