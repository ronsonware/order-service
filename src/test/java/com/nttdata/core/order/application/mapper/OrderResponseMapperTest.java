package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.OrderResponse;
import com.nttdata.core.order.application.dto.ProductResponse;
import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.entities.Product;
import com.nttdata.core.order.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderResponseMapperTest {

    private OrderResponseMapper orderResponseMapper;
    private ProductResponseMapper productResponseMapper;

    @BeforeEach
    void setUp() {
        // Mocking the ProductResponseMapper
        productResponseMapper = mock(ProductResponseMapper.class);
        orderResponseMapper = new OrderResponseMapper(productResponseMapper);
    }

    @Test
    void shouldMapOrderToOrderResponse() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Order order = Order.builder()
                .uuid(uuid)
                .status(Status.PENDING)
                .total(BigDecimal.valueOf(150.00))
                .build();

        Product product1 = Product.builder()
                .name("Product A")
                .description("Description A")
                .value(BigDecimal.valueOf(75.00))
                .build();

        Product product2 = Product.builder()
                .name("Product B")
                .description("Description B")
                .value(BigDecimal.valueOf(75.00))
                .build();

        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);
        order.setProducts(products);

        ProductResponse productResponse1 = ProductResponse.builder()
                .name("Product A")
                .description("Description A")
                .value(BigDecimal.valueOf(75.00))
                .build();
        ProductResponse productResponse2 = ProductResponse.builder()
                .name("Product B")
                .description("Description B")
                .value(BigDecimal.valueOf(75.00))
                .build();

        when(productResponseMapper.toResponse(product1)).thenReturn(productResponse1);
        when(productResponseMapper.toResponse(product2)).thenReturn(productResponse2);

        // Act
        OrderResponse result = orderResponseMapper.toResponse(order);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        assertEquals(Status.PENDING, result.getStatus());
        assertEquals(BigDecimal.valueOf(150.00), result.getTotal());
        assertNotNull(result.getProducts());
        assertEquals(2, result.getProducts().size());
        assertTrue(result.getProducts().contains(productResponse1));
        assertTrue(result.getProducts().contains(productResponse2));
    }

    @Test
    void shouldMapOrderWithNoProducts() {
        // Arrange
        Order order = Order.builder()
                .uuid(UUID.randomUUID())
                .status(Status.PENDING)
                .total(BigDecimal.valueOf(0.00))
                .build();

        // Act
        OrderResponse result = orderResponseMapper.toResponse(order);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(0.00), result.getTotal());
        assertTrue(result.getProducts().isEmpty());
    }
}
