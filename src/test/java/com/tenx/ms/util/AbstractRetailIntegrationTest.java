package com.tenx.ms.util;

import com.tenx.ms.commons.rest.RestConstants;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractRetailIntegrationTest extends AbstractSimpleIntegrationTest {
    public String getStockUrl(long storeId, long productId) {
        return String.format("%s/%s/stock/%s/%s", basePath(), RestConstants.VERSION_ONE, storeId, productId);
    }

    public String getOrdersUrl(long storeId) {
        return String.format("%s/%s/orders/%s", basePath(), RestConstants.VERSION_ONE, storeId);
    }

    public String getStoresUrl() {
        return String.format("%s/%s/stores/", basePath(), RestConstants.VERSION_ONE);
    }

    public void createStore(String name, long expectedId) throws IOException, URISyntaxException {
        postAndCheck(getStoresUrl(), "storeTests/create_store_" + name + ".json", HttpStatus.CREATED, new Resource("storeTests/create_store_response.json", "id", expectedId));
    }

    public void createProduct(long storeId, String name, long expectedId) throws IOException, URISyntaxException {
        postAndCheck(getProductsUrl(storeId), "productTests/create_product_" + name + ".json", HttpStatus.CREATED, new Resource("productTests/create_product_response.json", "id", expectedId));
    }

    public String getProductsUrl(long storeId) {
        return String.format("%s/%s/products/%s", basePath(), RestConstants.VERSION_ONE, storeId);
    }

    public void createOrder(String name, long storeId, long expectedId) throws IOException, URISyntaxException {
        postAndCheck(getOrdersUrl(storeId), "orderTests/create_order_" + name + ".json", HttpStatus.OK, new Resource("orderTests/create_order_" + name + "_response.json", "id", expectedId));
    }

    public void deleteStore(long storeId) throws IOException, URISyntaxException {
        deleteAndCheckEmpty(getStoresUrl() + "/" + storeId, HttpStatus.OK);
    }
}
