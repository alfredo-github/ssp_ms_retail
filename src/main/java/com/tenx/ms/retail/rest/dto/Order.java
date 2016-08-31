package com.tenx.ms.retail.rest.dto;

import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.domain.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("The order contains details about the purchaser as well as a listing of products and desired quantities being purchased")
public class Order {
    @ApiModelProperty("Order id")
    private Long orderId;

    @ApiModelProperty("Store id")
    private Long storeId;

    @ApiModelProperty("date/time of the order")
    private LocalDateTime orderDate;

    @ApiModelProperty("Status of the order: ORDERED, PACKING, SHIPPED")
    @NotNull
    private OrderStatusEnum status;

    @ApiModelProperty("Items in the order")
    private List<OrderItem> items;

    @NotNull
    @ApiModelProperty("the purchaser first name")
    @Pattern(regexp = "[a-zA-Z ]{1,100}")
    private String firstName;

    @NotNull
    @ApiModelProperty("the purchaser last name")
    @Pattern(regexp = "[a-zA-Z ]{1,100}")
    private String lastName;

    @Email
    @ApiModelProperty("email address of the purchas")
    private String email;

    @ApiModelProperty("valid 10 digit US phone #")
    @PhoneNumber
    private String phone;

    //<editor-fold desc="Boilerplate">
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long id) {
        this.orderId = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    //</editor-fold>
}
