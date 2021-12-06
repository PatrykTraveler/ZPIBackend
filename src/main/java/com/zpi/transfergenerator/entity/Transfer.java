package com.zpi.transfergenerator.entity;

import javax.persistence.*;

@Entity
@Table(name = "TRANSFER")
public class Transfer {
    public final static byte TYPE_TRANSFER = 1;
    public final static byte TYPE_TEMPLATE = 2;

    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private String iban;
    private String description;
    private float amount = 0;
    private String street;
    private String zipcode;
    private String city;
    private byte type = TYPE_TRANSFER;
    private boolean complete = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}