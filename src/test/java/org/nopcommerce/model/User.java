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


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpirationOfCreditCard() {
        return expirationOfCreditCard;
    }

    public void setExpirationOfCreditCard(String expirationOfCreditCard) {
        this.expirationOfCreditCard = expirationOfCreditCard;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }

    public User(String firstName, String lastName, String email, LocalDate dateOfBirth, String gender, String password, String companyName, boolean newsletter, String city, String address, String postalCode, String creditCard, String expirationOfCreditCard, String verificationCode, String country, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.password = password;
        this.companyName = companyName;
        this.newsletter = newsletter;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.creditCard = creditCard;
        this.expirationOfCreditCard = expirationOfCreditCard;
        this.verificationCode = verificationCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }
}
