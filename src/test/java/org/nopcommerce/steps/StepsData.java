package org.nopcommerce.steps;

import org.nopcommerce.model.User;
import org.nopcommerce.pagefragments.Products;
import org.nopcommerce.pageobjects.*;

import java.util.HashMap;
import java.util.Map;

public class StepsData {

    public String obtainedText;
    public User currentUser;
    public Map<String, User> UsersIdMap = new HashMap<>();

    public MainPage mainPage;
    public RegisterPage registerPage;
    public RegisterResult registerResult;
    public LoginPage loginPage;
    public MyAccountPage myAccountPage;
    public Products products;
    public ShippingDetailsPage shippingDetailsPage;

}
