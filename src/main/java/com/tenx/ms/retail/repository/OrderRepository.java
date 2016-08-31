package com.tenx.ms.retail.repository;

import com.tenx.ms.retail.domain.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {

}
