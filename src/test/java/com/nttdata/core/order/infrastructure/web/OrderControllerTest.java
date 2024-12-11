package com.nttdata.core.order.infrastructure.web;

import com.nttdata.core.order.application.dto.OrderResponse;
import com.nttdata.core.order.application.mapper.OrderResponseMapper;
import com.nttdata.core.order.application.services.OrderService;
import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderResponseMapper orderResponseMapper;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService, orderResponseMapper);
    }

    @Test
    void shouldListOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setStatus(Status.PENDING);
        order.setTotal(BigDecimal.valueOf(100.0));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUuid(order.getUuid());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setTotal(order.getTotal());

        Page<Order> page = new PageImpl<>(List.of(order), pageable, 1);
        when(orderService.list(pageable)).thenReturn(page);
        when(orderResponseMapper.toResponse(order)).thenReturn(orderResponse);

        // Act
        Page<OrderResponse> result = orderController.list(pageable);

        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals(orderResponse.getUuid(), result.getContent().get(0).getUuid());
        assertEquals(orderResponse.getStatus(), result.getContent().get(0).getStatus());
        assertEquals(orderResponse.getTotal(), result.getContent().get(0).getTotal());
    }

    @Test
    void shouldReturnEmptyListWhenNoOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(List.of(), pageable, 0);
        when(orderService.list(pageable)).thenReturn(page);

        // Act
        Page<OrderResponse> result = orderController.list(pageable);

        // Assert
        assertTrue(result.getContent().isEmpty());
    }
}
