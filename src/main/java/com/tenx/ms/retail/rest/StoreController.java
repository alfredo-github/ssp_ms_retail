package com.tenx.ms.retail.rest;

import com.tenx.ms.commons.exception.RESTCheckedException;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.rest.dto.Store;
import com.tenx.ms.retail.service.StoreService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Api for stores")
@RestController
@RequestMapping(value = RestConstants.VERSION_ONE + "/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @ApiOperation("Create a New Store")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Store created successfully"), @ApiResponse(code = 412, message = "Precondition failed, Validation failed"),
        @ApiResponse(code = 417, message = "Expectation failed, Validation failed"), @ApiResponse(code = 500, message = "Internal service error")})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceCreated<Long>> createStore(@Valid @RequestBody Store storeDto) throws RESTCheckedException {
        Long id = storeService.createNewStore(storeDto);
        return ResourceCreated.create(id, RestConstants.VERSION_ONE + "/store");
    }


    @ApiOperation("Get all stores")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "get all paginated stores success"), @ApiResponse(code = 500, message = "internal service error")})
    @RequestMapping(method = RequestMethod.GET)
    public Paginated<Store> getAllStores(Pageable page) {
        return storeService.getAllStores(page, RestConstants.VERSION_ONE + "/stores");
    }

    @ApiOperation("Get store by id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "get store success"), @ApiResponse(code = 500, message = "internal service error")})
    @RequestMapping(method = RequestMethod.GET, path = "/{storeId:\\d+}")
    public Store getStoreById(@ApiParam(name = "storeId", value = "store id") @PathVariable Long storeId) {
        return storeService.getStoreById(storeId);
    }

    @ApiOperation("Delete store")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "delete store success"), @ApiResponse(code = 500, message = "internal service error")})
    @RequestMapping(method = RequestMethod.DELETE, path = "/{storeId:\\d+}")
    public void deleteStore(@ApiParam(name = "storeId", value = "store id") @PathVariable Long storeId) {
        storeService.deleteStore(storeId);
    }
}
