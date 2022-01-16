package com.zpi.transfergenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Basket {
    private final Long id;
    private final String name;
    private final Date term;
    private final byte status;
    private final List<Long> transfers;

    public Basket(Long id, String name, Date term, byte status, List<Long> transfers) {
        this.id = id;
        this.name = name;
        this.term = term;
        this.status = status;
        this.transfers = transfers;
    }

    public Long getId() {
        return id;
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

    public List<Long> getTransfers() {
        return transfers;
    }
}
