package com.tenx.ms.retail.repository;

import com.tenx.ms.retail.domain.OrderItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItemEntity, Long> {

}
