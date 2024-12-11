package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.ProductResponse;
import com.nttdata.core.order.domain.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper {

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .value(product.getValue())
                .build();
    }
}

