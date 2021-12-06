package com.zpi.transfergenerator.repository;

import com.zpi.transfergenerator.entity.Transfer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransferRepository extends PagingAndSortingRepository<Transfer, Long> {
}
