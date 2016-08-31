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
public class ProductControllerTest extends AbstractRetailIntegrationTest {
    private static final long STORE_ID = 1;

    @Test
    @FlywayTest
    public void testProductCreation() throws IOException, URISyntaxException {
        createStore("belmont", STORE_ID);
        createProduct(STORE_ID, "hammer", 1);
        getAndCheck(getProductsUrl(STORE_ID), HttpStatus.OK, "productTests/find_products_response.json");
    }

    @Test
    @FlywayTest
    public void testProductCreationValidation() throws IOException, URISyntaxException {
        createStore("belmont", STORE_ID);
        postAndCheckFail(getProductsUrl(STORE_ID), "productTests/create_product_validation_error.json", HttpStatus.PRECONDITION_FAILED, "productTests/create_product_validation_error_response.json");
        getAndCheck(getProductsUrl(STORE_ID), HttpStatus.OK, "productTests/list_products_empty_response.json");
    }

    @Test
    @FlywayTest
    public void testProductGetById() throws IOException, URISyntaxException {
        long productId = 1;
        createStore("belmont", STORE_ID);
        createProduct(STORE_ID, "hammer", productId);
        getAndCheck(getProductsUrl(STORE_ID) + "/" + productId, HttpStatus.OK, "productTests/get_product_by_id_response.json");
    }

    @Test
    @FlywayTest
    public void testProductGetByName() throws IOException, URISyntaxException {
        createStore("belmont", STORE_ID);
        createProduct(STORE_ID, "hammer", 1);
        createProduct(STORE_ID, "drill", 2);
        getAndCheck(getProductsUrl(STORE_ID) + "?name=hammer", HttpStatus.OK, "productTests/get_product_by_name_hammer_response.json");
        getAndCheck(getProductsUrl(STORE_ID) + "?name=xyz", HttpStatus.OK, "productTests/get_product_by_name_xyz_response.json");
        getAndCheck(getProductsUrl(STORE_ID) + "?name=StanLEY", HttpStatus.OK, "productTests/get_product_by_name_stanley_response.json");
    }
}
