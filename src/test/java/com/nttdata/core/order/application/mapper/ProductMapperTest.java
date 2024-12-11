package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.ProductDataEvent;
import com.nttdata.core.order.domain.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    void shouldMapProductDataEventToProductEntity() {
        // Arrange
        ProductDataEvent productDataEvent = new ProductDataEvent();
        productDataEvent.setName("Product A");
        productDataEvent.setDescription("Description A");
        productDataEvent.setValue(BigDecimal.valueOf(100.50));

        // Act
        Product result = productMapper.toEntity(productDataEvent);

        // Assert
        assertNotNull(result);
        assertEquals("Product A", result.getName());
        assertEquals("Description A", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.50), result.getValue());
    }

    @Test
    void shouldMapProductDataEventWithNullFields() {
        // Arrange
        ProductDataEvent productDataEvent = new ProductDataEvent();
        productDataEvent.setName(null);
        productDataEvent.setDescription(null);
        productDataEvent.setValue(null);

        // Act
        Product result = productMapper.toEntity(productDataEvent);

        // Assert
        assertNotNull(result);
        assertNull(result.getName());
        assertNull(result.getDescription());
        assertNull(result.getValue());
    }
}
