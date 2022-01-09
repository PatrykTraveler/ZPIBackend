package com.zpi.transfergenerator.controller;

import com.zpi.transfergenerator.model.Basket;
import com.zpi.transfergenerator.model.BasketConverter;
import com.zpi.transfergenerator.model.TransferConverter;
import com.zpi.transfergenerator.repository.BasketRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketRepository basketRepository;
    private final BasketConverter basketConverter;
    private final TransferConverter transferConverter;

    public BasketController(BasketRepository basketRepository, BasketConverter basketConverter, TransferConverter transferConverter) {
        this.basketRepository = basketRepository;
        this.basketConverter = basketConverter;
        this.transferConverter = transferConverter;
    }

    @GetMapping
    public ResponseEntity<List<com.zpi.transfergenerator.model.Basket>> getList(@RequestParam(defaultValue = "1000") int limit,
                                                                                @RequestParam(defaultValue = "0") int start) {
        final var pageable = PageRequest.of(start, limit);
        final var basketPage = basketRepository.findAll(pageable);
        final var result = basketPage.getContent().stream().map(basketConverter::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Basket> create(@RequestBody Basket basket) {
        final var entity = basketRepository.save(basketConverter.fromModel(basket));

        return new ResponseEntity<>(basketConverter.toModel(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Error> update(@PathVariable Long id, @RequestBody com.zpi.transfergenerator.model.Basket basket) {
        final var optionalBasketEntity = basketRepository.findById(id);
        if (optionalBasketEntity.isPresent()) {
            final var newBasket = optionalBasketEntity.get();
            newBasket.setTerm(basket.getTerm());
            newBasket.setStatus(basket.getStatus());
            newBasket.setName(basket.getName());
            final var transfers = basket.getTransfers().stream().map(transferConverter::fromModel).collect(Collectors.toList());
            Optional.ofNullable(newBasket.getTransfers()).ifPresentOrElse(tr -> tr.addAll(transfers), () -> newBasket.setTransfers(transfers));
            basketRepository.save(newBasket);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Error> delete(@PathVariable Long id) {
        final var optionalBasketEntity = basketRepository.findById(id);
        if (optionalBasketEntity.isPresent()) {
            final var basketEntity = optionalBasketEntity.get();
            basketRepository.delete(basketEntity);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
