package com.zpi.transfergenerator.model;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BasketConverter {
    private final TransferConverter transferConverter;

    public BasketConverter(TransferConverter transferConverter) {
        this.transferConverter = transferConverter;
    }

    public Basket toModel(com.zpi.transfergenerator.entity.Basket basket) {
        return new Basket(basket.getId(), basket.getName(), basket.getTerm(), basket.getStatus(), basket.getTransfers().stream().map(transferConverter::toModel).collect(Collectors.toList()));
    }

    public com.zpi.transfergenerator.entity.Basket fromModel(Basket basket) {
        final var basketEntity = new com.zpi.transfergenerator.entity.Basket();
        basketEntity.setTransfers(basket.getTransfers().stream().map(transferConverter::fromModel).collect(Collectors.toList()));
        basketEntity.setName(basket.getName());
        basketEntity.setStatus(basket.getStatus());
        basketEntity.setTerm(basket.getTerm());

        return basketEntity;
    }
}
