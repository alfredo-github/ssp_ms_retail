package com.tenx.ms.retail.stock;

import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.util.AbstractRetailIntegrationTest;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class StockControllerTest extends AbstractRetailIntegrationTest {
    private static final long STORE_ID = 1;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @FlywayTest
    public void testUpdateProductQuantity() throws IOException, URISyntaxException {
        long hammerId = 1;
        createStore("belmont", STORE_ID);
        createProduct(STORE_ID, "hammer", hammerId);
        createProduct(STORE_ID, "drill", 2);

        postAndCheckEmpty(getStockUrl(STORE_ID, hammerId), "productTests/update_qty.json", HttpStatus.OK);
        // Check result using repository, no endpoint for get exists
        ProductEntity hammerEntity = productRepository.findOne(hammerId);
        assertEquals("count mismatch", (Integer) 300, hammerEntity.getCount());
    }

    @Test
    @FlywayTest
    public void testUpdateProductQuantityValidations() throws IOException, URISyntaxException {
        long hammerId = 1;
        createStore("belmont", STORE_ID);
        createProduct(STORE_ID, "hammer", hammerId);
        createProduct(STORE_ID, "drill", 2);

        postAndCheckFail(getStockUrl(STORE_ID, hammerId), "productTests/update_qty_validation_error.json", HttpStatus.PRECONDITION_FAILED, "productTests/update_qty_validation_error_response.json");
        // Check result using repository, no endpoint for get exists
        ProductEntity hammerEntity = productRepository.findOne(hammerId);
        assertEquals("count mismatch", (Integer) 0, hammerEntity.getCount());
    }

}
