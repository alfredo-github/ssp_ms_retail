package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel("Store")
public class Store {
    @ApiModelProperty("Store id")
    private Long id;

    @ApiModelProperty("Store name")
    @Length(max = 64)
    @NotNull
    private String name;

    //<editor-fold desc="Boilerplate">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //</editor-fold>
}
