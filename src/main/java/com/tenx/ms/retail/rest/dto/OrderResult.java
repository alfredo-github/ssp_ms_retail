package com.tenx.ms.retail.rest.dto;

import com.tenx.ms.retail.domain.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Result of creating an order")
public class OrderResult {
    @ApiModelProperty("Order id")
    private Long orderId;

    @ApiModelProperty("Status of the order: ORDERED, PACKING, SHIPPED")
    private OrderStatusEnum status;

    //<editor-fold desc="Boilerplate">
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
    //</editor-fold>
}
