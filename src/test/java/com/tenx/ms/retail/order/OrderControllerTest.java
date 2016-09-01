package com.tenx.ms.retail.order;

import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.repository.OrderRepository;
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
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class OrderControllerTest extends AbstractRetailIntegrationTest {
    private static final long STORE_ID = 1;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @FlywayTest
    public void testCreateOrder() throws IOException, URISyntaxException {
        long hammerId = 1;
        createStore("belmont", STORE_ID);
        createProduct(STORE_ID, "hammer", hammerId);
        createProduct(STORE_ID, "drill", 2);

        long sampleOrder = 1;
        createOrder("sample", STORE_ID, sampleOrder);
        // Check result using repository, no endpoint for get exists
        OrderEntity orderEntity = orderRepository.findOne(sampleOrder);
        assertEquals("items count differs", 1, orderEntity.getItems().size());
    }

    @Test
    @FlywayTest
    public void testCreateOrderValidation() throws IOException, URISyntaxException {
        long hammerId = 1;
        createStore("belmont", STORE_ID);
        createProduct(STORE_ID, "hammer", hammerId);
        createProduct(STORE_ID, "drill", 2);

        postAndCheckFail(getOrdersUrl(STORE_ID), "orderTests/create_order_validation_error.json", HttpStatus.PRECONDITION_FAILED, "orderTests/create_order_validation_error_response.json");
        // Check result using repository, no endpoint for get exists
        Iterable<OrderEntity> orderEntities = orderRepository.findAll();
        assertFalse("expeced 0 orders", orderEntities.iterator().hasNext());
    }

}
