package com.zpi.transfergenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.zpi.transfergenerator.entity.Transfer.TYPE_TRANSFER;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transfer {
    private final Long id;
    private final String name;
    private final String iban;
    private final String description;
    private final float amount = 0;
    private final String street;
    private final String zipcode;
    private final String city;
    private final byte type = TYPE_TRANSFER;
    private final boolean complete = false;

    public Transfer(Long id, String name, String iban, String description, String street, String zipcode, String city) {
        this.id = id;
        this.name = name;
        this.iban = iban;
        this.description = description;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIban() {
        return iban;
    }

    public String getDescription() {
        return description;
    }

    public float getAmount() {
        return amount;
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

    public byte getType() {
        return type;
    }

    public boolean isComplete() {
        return complete;
    }
}
