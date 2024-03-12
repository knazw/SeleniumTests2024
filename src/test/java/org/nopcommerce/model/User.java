package org.nopcommerce.model;

import java.time.LocalDate;

public class User {
    public String firstName;
    public String lastName;

    public String email;
//    @JsonFormat
//            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public LocalDate dateOfBirth;

    public String gender;

    public String password;
    public String companyName;

    public boolean newsletter;

    public String city;

    public String address;

    public String postalCode;

    public String creditCard;

    public String expirationOfCreditCard;

    public String verificationCode;
    public String country;
    public String phoneNumber;
}
