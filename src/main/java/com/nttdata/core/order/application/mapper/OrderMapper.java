package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.OrderCreateEvent;
import com.nttdata.core.order.application.dto.ProductDataEvent;
import com.nttdata.core.order.domain.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductMapper productMapper;

    public Order toEntity(OrderCreateEvent event) {
        return Order.builder()
                .uuid(event.getUuid())
                .status(event.getStatus())
                .total(event.getProducts().stream()
                        .map(ProductDataEvent::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .products(event.getProducts().stream()
                        .map(productMapper::toEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}

