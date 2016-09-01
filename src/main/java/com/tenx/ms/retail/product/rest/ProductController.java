package com.tenx.ms.retail.product.rest;

import com.tenx.ms.commons.exception.RESTCheckedException;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Api for products")
@RestController
@RequestMapping(value = RestConstants.VERSION_ONE + "/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("Create a new product")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Product created successfully"), @ApiResponse(code = 412, message = "Precondition failed, Validation failed"),
        @ApiResponse(code = 417, message = "Expectation failed, Validation failed"), @ApiResponse(code = 500, message = "Internal service error")})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, path = "/{storeId:\\d+}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceCreated<Long>> createProduct(@Valid @RequestBody Product productDto, @ApiParam(name = "storeId", value = "store id") @PathVariable Long storeId) throws RESTCheckedException {
        productDto.setStoreId(storeId);
        Long id = productService.createNewProduct(productDto);
        return ResourceCreated.create(id, RestConstants.VERSION_ONE + "/product");
    }


    @ApiOperation("Get products list by store and name (optional)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "get all paginated products success"), @ApiResponse(code = 500, message = "internal service error")})
    @RequestMapping(method = RequestMethod.GET, path = "/{storeId:\\d+}")
    public Paginated<Product> getProducts(Pageable page, @PathVariable Long storeId, @ApiParam(name = "name", value = "name of store to look for") @RequestParam(value = "name", required = false) String name) {
        return productService.getProducts(storeId, name, page, RestConstants.VERSION_ONE + "/products");
    }


    @ApiOperation("Get product by product and store id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "get product success"), @ApiResponse(code = 500, message = "internal service error")})
    @RequestMapping(method = RequestMethod.GET, path = "/{storeId:\\d+}/{productId:\\d+}")
    public Product getProductById(@ApiParam(name = "storeId", value = "store id") @PathVariable Long storeId, @ApiParam(name = "productId", value = "product id") @PathVariable Long productId) {
        return productService.getProductByIdAndStoreId(productId, storeId);
    }
}
