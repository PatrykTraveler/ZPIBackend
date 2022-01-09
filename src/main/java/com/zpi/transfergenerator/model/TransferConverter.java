package com.zpi.transfergenerator.model;

import org.springframework.stereotype.Component;

@Component
public class TransferConverter {
    public Transfer toModel(com.zpi.transfergenerator.entity.Transfer transfer) {
        return new Transfer(transfer.getId(), transfer.getName(), transfer.getIban(), transfer.getDescription(), transfer.getStreet(), transfer.getZipcode(), transfer.getCity());
    }

    public com.zpi.transfergenerator.entity.Transfer fromModel(Transfer transfer) {
        final var transferEntity = new com.zpi.transfergenerator.entity.Transfer();
        transferEntity.setName(transfer.getName());
        transferEntity.setAmount(transfer.getAmount());
        transferEntity.setCity(transfer.getCity());
        transferEntity.setComplete(transfer.isComplete());
        transferEntity.setDescription(transfer.getDescription());
        transferEntity.setIban(transfer.getIban());
        transferEntity.setStreet(transfer.getStreet());
        transferEntity.setType(transfer.getType());
        transferEntity.setZipcode(transfer.getZipcode());
        transferEntity.setType(transfer.getType());

        return transferEntity;
    }
}
