package com.tenx.ms.retail.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

@ApiModel("Stock update for product")
public class Stock {
    @ApiModelProperty("Product id")
    private Long productId;

    @ApiModelProperty("Store id")
    private Long storeId;

    @ApiModelProperty("Inventory count for product")
    @Min(0)
    private Integer count;

    //<editor-fold desc="Boilerplate">

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    //</editor-fold>
}
