package com.tenx.ms.retail.order.repository;

import com.tenx.ms.retail.order.domain.OrderItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItemEntity, Long> {

}
