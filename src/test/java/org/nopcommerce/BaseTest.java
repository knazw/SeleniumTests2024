package org.nopcommerce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.nopcommerce.helpers.FileOperations;
import org.nopcommerce.model.Product;
import org.nopcommerce.model.ShippingMethod;
import org.nopcommerce.model.User;
import org.nopcommerce.pageobjects.MainPage;

import java.time.LocalDate;

import static org.utilities.BaseTestHelpers.getBrowser;

public class BaseTest {
    MainPage mainPage;

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
        mainPage.quit();
    }



    protected static User createUser() {

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
