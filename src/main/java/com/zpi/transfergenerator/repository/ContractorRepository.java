package com.zpi.transfergenerator.repository;

import com.zpi.transfergenerator.entity.Contractor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContractorRepository extends PagingAndSortingRepository<Contractor, Long> {
}
