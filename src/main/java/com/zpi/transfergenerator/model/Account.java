package com.zpi.transfergenerator.model;

public class Account {
    private final String iban;
    private final String name;

    public Account(String iban, String name) {
        this.iban = iban;
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public String getName() {
        return name;
    }
}
