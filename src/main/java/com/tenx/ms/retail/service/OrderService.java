package com.tenx.ms.retail.service;

import com.tenx.ms.retail.domain.OrderEntity;
import com.tenx.ms.retail.domain.OrderItemEntity;
import com.tenx.ms.retail.domain.OrderStatusEnum;
import com.tenx.ms.retail.domain.ProductEntity;
import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.repository.OrderRepository;
import com.tenx.ms.retail.repository.ProductRepository;
import com.tenx.ms.retail.repository.StoreRepository;
import com.tenx.ms.retail.rest.dto.Order;
import com.tenx.ms.retail.rest.dto.OrderItem;
import com.tenx.ms.retail.rest.dto.OrderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public OrderResult createOrder(Order orderDto) {
        OrderEntity orderEntity = convertToOrderEntity(orderDto);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setStatus(OrderStatusEnum.ORDERED);
        orderRepository.save(orderEntity);

        OrderResult result = new OrderResult();
        result.setOrderId(orderEntity.getId());
        result.setStatus(orderEntity.getStatus());
        return result;
    }

    private OrderEntity convertToOrderEntity(Order orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        StoreEntity storeEntity = storeRepository.findById(orderDto.getStoreId()).get();
        orderEntity.setStore(storeEntity);
        orderEntity.setOrderDate(orderDto.getOrderDate());
        orderEntity.setStatus(orderDto.getStatus());
        orderEntity.setItems(orderDto.getItems().stream().map(orderItem -> convertToOrderItemEntity(orderItem, orderEntity)).collect(Collectors.toList()));
        orderEntity.setFirstName(orderDto.getFirstName());
        orderEntity.setLastName(orderDto.getLastName());
        orderEntity.setEmail(orderDto.getEmail());
        orderEntity.setPhone(orderDto.getPhone());
        return orderEntity;
    }

    private OrderItemEntity convertToOrderItemEntity(OrderItem orderItemDto, OrderEntity orderEntity) {
        OrderItemEntity itemEntity = new OrderItemEntity();
        itemEntity.setOrder(orderEntity);
        ProductEntity productEntity = productRepository.findOne(orderItemDto.getProductId());
        itemEntity.setProduct(productEntity);
        itemEntity.setCount(orderItemDto.getCount());
        return itemEntity;
    }
}
