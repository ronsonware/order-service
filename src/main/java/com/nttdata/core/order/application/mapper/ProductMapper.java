package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.ProductDataEvent;
import com.nttdata.core.order.domain.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDataEvent event) {
        return Product.builder()
                .name(event.getName())
                .description(event.getDescription())
                .value(event.getValue())
                .build();
    }
}

