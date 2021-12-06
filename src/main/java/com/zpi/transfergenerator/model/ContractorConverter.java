package com.zpi.transfergenerator.model;

import org.springframework.stereotype.Component;

@Component
public class ContractorConverter {
    public Contractor toModel(com.zpi.transfergenerator.entity.Contractor contractor) {
        return new Contractor(contractor.getFirstName(), contractor.getLastName(), contractor.getCompany(), contractor.getStreet(), contractor.getZipcode(), contractor.getCity(), contractor.getIban());
    }

    public com.zpi.transfergenerator.entity.Contractor fromModel(Contractor contractor) {
        final var contractorEntity = new com.zpi.transfergenerator.entity.Contractor();
        contractorEntity.setCity(contractor.getCity());
        contractorEntity.setCompany(contractor.getCompany());
        contractorEntity.setFirstName(contractor.getFirstName());
        contractorEntity.setLastName(contractor.getLastName());
        contractorEntity.setStreet(contractor.getStreet());
        contractorEntity.setIban(contractor.getIban());
        contractorEntity.setZipcode(contractor.getZipcode());

        return contractorEntity;
    }
}
