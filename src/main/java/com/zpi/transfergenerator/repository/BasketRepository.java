package com.zpi.transfergenerator.repository;

import com.zpi.transfergenerator.entity.Basket;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BasketRepository extends PagingAndSortingRepository<Basket, Long> {
}
