package com.tenx.ms.retail.product.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@ApiModel("Product that is associated to a store")
public class Product {
    @ApiModelProperty("Product id")
    private Long productId;

    @ApiModelProperty("Store id")
    private Long storeId;

    @ApiModelProperty("Product name")
    @Length(max = 64)
    @NotNull
    private String name;

    @ApiModelProperty("Product description")
    @Length(max = 256)
    private String description;

    @ApiModelProperty("Product stock keeping unit code")
    @Pattern(regexp = "[0-9a-zA-Z]{5,10}")
    private String sku;

    @ApiModelProperty("Product price")
    @NotNull
    @Min(0)
    private BigDecimal price;

    //<editor-fold desc="Boilerplate">
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //</editor-fold>
}
