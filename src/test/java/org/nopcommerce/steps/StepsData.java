package org.nopcommerce.steps;

import org.nopcommerce.model.User;
import org.nopcommerce.pageobjects.*;

import java.util.HashMap;
import java.util.Map;

public class StepsData {

    public User currentUser;
    public Map<String, User> UsersIdMap = new HashMap<>();

    public MainPage mainPage;
    public RegisterPage registerPage;
    public RegisterResult registerResult;
    public LoginPage loginPage;
    public MyAccountPage myAccountPage;
}
