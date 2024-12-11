package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.ProductResponse;
import com.nttdata.core.order.domain.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductResponseMapperTest {

    private ProductResponseMapper productResponseMapper;

    @BeforeEach
    void setUp() {
        productResponseMapper = new ProductResponseMapper();
    }

    @Test
    void shouldMapProductToProductResponse() {
        // Arrange
        Product product = Product.builder()
                .name("Product A")
                .description("Description A")
                .value(BigDecimal.valueOf(100.00))
                .build();

        // Act
        ProductResponse result = productResponseMapper.toResponse(product);

        // Assert
        assertNotNull(result);
        assertEquals("Product A", result.getName());
        assertEquals("Description A", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.00), result.getValue());
    }
}

