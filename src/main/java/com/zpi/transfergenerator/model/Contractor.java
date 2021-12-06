package com.zpi.transfergenerator.model;

public class Contractor {
    private final String firstName;
    private final String lastName;
    private final String company;
    private final String street;
    private final String zipcode;
    private final String city;
    private final String iban;

    public Contractor(String firstName, String lastName, String company, String street, String zipcode, String city, String iban) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.iban = iban;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getIban() {
        return iban;
    }
}
