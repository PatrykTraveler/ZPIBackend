package com.zpi.transfergenerator.model;

import com.zpi.transfergenerator.entity.Transfer;
import com.zpi.transfergenerator.repository.TransferRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BasketConverter {
    private final TransferConverter transferConverter;
    private final TransferRepository transferRepository;

    public BasketConverter(TransferConverter transferConverter, TransferRepository transferRepository) {
        this.transferConverter = transferConverter;
        this.transferRepository = transferRepository;
    }

    public Basket toModel(com.zpi.transfergenerator.entity.Basket basket) {
        final var transfers = basket.getTransfers().stream()
                .map(Transfer::getId)
                .collect(Collectors.toList());

        return new Basket(basket.getId(), basket.getName(), basket.getTerm(), basket.getStatus(), transfers);
    }

    public com.zpi.transfergenerator.entity.Basket fromModel(Basket basket) {
        final var basketEntity = new com.zpi.transfergenerator.entity.Basket();
        final var transfers = basket.getTransfers().stream()
                .map(transferRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        basketEntity.setTransfers(transfers);
        basketEntity.setName(basket.getName());
        basketEntity.setStatus(basket.getStatus());
        basketEntity.setTerm(basket.getTerm());

        return basketEntity;
    }
}
