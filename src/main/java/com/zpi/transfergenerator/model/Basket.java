package com.zpi.transfergenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Basket {
    private final String name;
    private final Date term;
    private final byte status;
    private final List<Transfer> transfers;

    public Basket(String name, Date term, byte status, List<Transfer> transfers) {
        this.name = name;
        this.term = term;
        this.status = status;
        this.transfers = transfers;
    }

    public String getName() {
        return name;
    }

    public Date getTerm() {
        return term;
    }

    public byte getStatus() {
        return status;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }
}