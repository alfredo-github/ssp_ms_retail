package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Listing of products and desired quantities being purchased")
public class OrderItem {
    @ApiModelProperty("Product id")
    private Long productId;

    @ApiModelProperty("Requested count for the product")
    private Integer count;

    //<editor-fold desc="Boilerplate">
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    //</editor-fold>
}
