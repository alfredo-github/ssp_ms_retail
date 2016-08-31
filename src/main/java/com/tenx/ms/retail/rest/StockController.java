package com.tenx.ms.retail.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.rest.dto.Stock;
import com.tenx.ms.retail.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Api for stock")
@RestController
@RequestMapping(value = RestConstants.VERSION_ONE + "/stock")
public class StockController {
    @Autowired
    private ProductService productService;

    @ApiOperation("Update Product Quantity")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "update quantity success"), @ApiResponse(code = 500, message = "internal service error")})
    @RequestMapping(method = RequestMethod.POST, path = "/{storeId:\\d+}/{productId:\\d+}")
    public void updateProductQuantity(@Valid @RequestBody Stock stockDto, @ApiParam(name = "storeId", value = "store id") @PathVariable Long storeId, @ApiParam(name = "productId", value = "product id") @PathVariable Long productId) {
        stockDto.setProductId(productId);
        stockDto.setStoreId(storeId);
        productService.updateStock(stockDto);
    }
}
