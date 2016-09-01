package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.exception.RESTCheckedException;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderResult;
import com.tenx.ms.retail.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Api for orders")
@RestController
@RequestMapping(value = RestConstants.VERSION_ONE + "/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("Create a new Order")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Order created successfully"), @ApiResponse(code = 412, message = "Precondition failed, Validation failed"),
        @ApiResponse(code = 417, message = "Expectation failed, Validation failed"), @ApiResponse(code = 500, message = "Internal service error")})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, path = "/{storeId:\\d+}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public OrderResult createOrder(@Valid @RequestBody Order orderDto, @ApiParam(name = "storeId", value = "store id") @PathVariable Long storeId) throws RESTCheckedException {
        orderDto.setStoreId(storeId);
        return orderService.createOrder(orderDto);
    }
}
