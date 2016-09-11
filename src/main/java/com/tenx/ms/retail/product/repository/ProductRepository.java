package com.tenx.ms.retail.product.repository;

import com.tenx.ms.retail.product.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByIdAndStoreId(Long id, Long storeId);

    Page<ProductEntity> findByStoreId(Long storeId, Pageable pageable);

    Page<ProductEntity> findByStoreIdAndNameContaining(Long storeId, String nameLike, Pageable pageable);
}
