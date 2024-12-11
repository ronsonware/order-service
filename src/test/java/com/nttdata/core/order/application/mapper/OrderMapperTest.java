package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.OrderCreateEvent;
import com.nttdata.core.order.application.dto.ProductDataEvent;
import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.entities.Product;
import com.nttdata.core.order.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderMapperTest {

    private OrderMapper orderMapper;
    private ProductMapper productMapper;
    private UUID oderUuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        productMapper = mock(ProductMapper.class);
        orderMapper = new OrderMapper(productMapper);
    }

    @Test
    void shouldMapOrderCreateEventToOrderEntity() {
        // Arrange
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent();
        orderCreateEvent.setUuid(oderUuid);
        orderCreateEvent.setStatus(Status.PENDING);

        ProductDataEvent product1 = new ProductDataEvent();
        product1.setValue(BigDecimal.valueOf(50));

        ProductDataEvent product2 = new ProductDataEvent();
        product2.setValue(BigDecimal.valueOf(30));
        orderCreateEvent.setProducts(Set.of(product1, product2));

        when(productMapper.toEntity(product1)).thenReturn(Product.builder().name("Product 1").build());
        when(productMapper.toEntity(product2)).thenReturn(Product.builder().name("Product 2").build());

        // Act
        Order result = orderMapper.toEntity(orderCreateEvent);

        // Assert
        assertNotNull(result);
        assertEquals(oderUuid, result.getUuid());
        assertEquals(Status.PENDING, result.getStatus());
        assertEquals(BigDecimal.valueOf(80), result.getTotal());
        assertEquals(2, result.getProducts().size());
        verify(productMapper, times(2)).toEntity(any());
    }

    @Test
    void shouldReturnOrderWithZeroTotalWhenNoProducts() {
        // Arrange
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent();
        orderCreateEvent.setUuid(oderUuid);
        orderCreateEvent.setStatus(Status.PENDING);
        orderCreateEvent.setProducts(Collections.emptySet());

        // Act
        Order result = orderMapper.toEntity(orderCreateEvent);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getTotal());
        assertEquals(0, result.getProducts().size());
    }
}
