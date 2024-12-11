package com.nttdata.core.order.application.mapper;

import com.nttdata.core.order.application.dto.OrderResponse;
import com.nttdata.core.order.domain.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderResponseMapper {

    private final ProductResponseMapper productResponseMapper;

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .uuid(order.getUuid())
                .status(order.getStatus())
                .total(order.getTotal())
                .products(Objects.nonNull(order.getProducts())
                    ? order.getProducts().stream()
                        .map(productResponseMapper::toResponse)
                        .collect(Collectors.toSet())
                    : Set.of()
                )
                .build();
    }
}

