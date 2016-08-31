package com.tenx.ms.retail.repository;

import com.tenx.ms.retail.domain.StoreEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<StoreEntity, Long> {
    Optional<StoreEntity> findById(Long id);
}
