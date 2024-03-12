package org.nopcommerce.model;

public class ShippingMethod {
    public String postalCode;

    public String shippingMethod;

    public String country;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShippingAddressFull() {
        return "to "+country + ", "+postalCode + " via "+shippingMethod;
    }
}
