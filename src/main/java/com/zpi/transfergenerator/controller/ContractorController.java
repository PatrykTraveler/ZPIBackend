package com.zpi.transfergenerator.controller;

import com.zpi.transfergenerator.model.Contractor;
import com.zpi.transfergenerator.model.ContractorConverter;
import com.zpi.transfergenerator.repository.ContractorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contractor")
public class ContractorController {
    private final ContractorRepository contractorRepository;
    private final ContractorConverter contractorConverter;

    public ContractorController(ContractorRepository contractorRepository, ContractorConverter contractorConverter) {
        this.contractorRepository = contractorRepository;
        this.contractorConverter = contractorConverter;
    }

    @GetMapping
    public ResponseEntity<List<com.zpi.transfergenerator.model.Contractor>> getList(@RequestParam(defaultValue = "1000") int limit,
                                                                                    @RequestParam(defaultValue = "0") int start) {
        final var pageable = PageRequest.of(start, limit);
        final var page = contractorRepository.findAll(pageable);
        final var result = page.getContent().stream().map(contractorConverter::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Error> create(@RequestBody Contractor contractor) {
        contractorRepository.save(contractorConverter.fromModel(contractor));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Error> update(@PathVariable Long id, @RequestBody com.zpi.transfergenerator.model.Contractor contractor) {
        final var optionalContractorEntity = contractorRepository.findById(id);
        if (optionalContractorEntity.isPresent()) {
            final var newContractor = contractorConverter.fromModel(contractor);
            newContractor.setId(optionalContractorEntity.get().getId());
            contractorRepository.save(newContractor);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id) {
        final var optionalContractorEntity = contractorRepository.findById(id);
        if (optionalContractorEntity.isPresent()) {
            final var contractorEntity = optionalContractorEntity.get();
            contractorRepository.delete(contractorEntity);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
