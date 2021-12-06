package com.zpi.transfergenerator.controller;

import com.zpi.transfergenerator.model.Transfer;
import com.zpi.transfergenerator.model.TransferConverter;
import com.zpi.transfergenerator.repository.TransferRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    private final TransferRepository transferRepository;
    private final TransferConverter transferConverter;

    public TransferController(TransferRepository transferRepository, TransferConverter transferConverter) {
        this.transferRepository = transferRepository;
        this.transferConverter = transferConverter;
    }

    @GetMapping
    public ResponseEntity<List<Transfer>> getList(@RequestParam(defaultValue = "1000") int limit,
                                                  @RequestParam(defaultValue = "0") int start) {
        final var pageable = PageRequest.of(start, limit);
        final var basketPage = transferRepository.findAll(pageable);
        final var result = basketPage.getContent().stream().map(transferConverter::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Error> create(@RequestBody Transfer transfer) {
        transferRepository.save(transferConverter.fromModel(transfer));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Error> update(@PathVariable Long id, @RequestBody Transfer transfer) {
        final var optionalTransferEntity = transferRepository.findById(id);
        if (optionalTransferEntity.isPresent()) {
            final var newTransfer = transferConverter.fromModel(transfer);
            newTransfer.setId(optionalTransferEntity.get().getId());
            transferRepository.save(newTransfer);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id) {
        final var optionalTransferEntity = transferRepository.findById(id);
        if (optionalTransferEntity.isPresent()) {
            final var contractorEntity = optionalTransferEntity.get();
            transferRepository.delete(contractorEntity);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
