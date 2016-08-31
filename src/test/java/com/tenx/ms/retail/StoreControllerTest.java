package com.tenx.ms.retail;

import com.tenx.ms.util.AbstractRetailIntegrationTest;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.net.URISyntaxException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class StoreControllerTest extends AbstractRetailIntegrationTest {
    @Test
    @FlywayTest
    public void testStoreCreation() throws IOException, URISyntaxException {
        createStore("belmont", 1);
        getAndCheck(getStoresUrl(), HttpStatus.OK, "storeTests/find_stores_response.json");
    }

    @Test
    @FlywayTest
    public void testStoreCreationValidation() throws IOException, URISyntaxException {
        postAndCheckFail(getStoresUrl(), "storeTests/create_store_validation_error.json", HttpStatus.PRECONDITION_FAILED, "storeTests/create_store_validation_error_response.json");
        getAndCheck(getStoresUrl(), HttpStatus.OK, "storeTests/find_stores_empty_response.json");
    }

    @Test
    @FlywayTest
    public void testStoreGetById() throws IOException, URISyntaxException {
        createStore("belmont", 1);
        getAndCheck(getStoresUrl() + "/1", HttpStatus.OK, "storeTests/get_store_by_id_response.json");
    }

    @Test
    @FlywayTest
    public void testStoreGetByIdNotFound() throws IOException, URISyntaxException {
        createStore("belmont", 1);
        getAndCheckFail(getStoresUrl() + "/100", HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testDeleteStore() throws IOException, URISyntaxException {
        long hammerId = 1;
        long storeId = 1;
        createStore("belmont", storeId);
        createProduct(storeId, "hammer", hammerId);

        deleteStore(storeId);
        getAndCheckFail(getStoresUrl() + "/" + storeId, HttpStatus.NOT_FOUND);
        getAndCheck(getProductsUrl(storeId), HttpStatus.OK, "productTests/list_products_empty_response.json");
    }
}
