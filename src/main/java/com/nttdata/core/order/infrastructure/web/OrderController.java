package com.nttdata.core.order.infrastructure.web;

import com.nttdata.core.order.application.dto.OrderResponse;
import com.nttdata.core.order.application.mapper.OrderResponseMapper;
import com.nttdata.core.order.application.services.OrderService;
import com.nttdata.core.order.domain.entities.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
@Tag(name = "Orders Resource")
public class OrderController {

    private final OrderService orderService;
    private final OrderResponseMapper orderResponseMapper;

    @Operation(summary = "List orders", description = "Return orders by page params")
    @GetMapping
    public Page<OrderResponse> list(Pageable pageable) {
        log.info("Fetching orders with pagination: {}", pageable);

        Page<Order> page = orderService.list(pageable);
        List<OrderResponse> responses = page.getContent().stream()
                .map(orderResponseMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }

}

