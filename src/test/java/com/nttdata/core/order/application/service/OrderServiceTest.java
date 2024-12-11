package com.nttdata.core.order.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nttdata.core.order.application.services.OrderService;
import com.nttdata.core.order.application.usecases.OrderCreateUserCase;
import com.nttdata.core.order.application.usecases.OrderListUserCase;
import com.nttdata.core.order.domain.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class OrderServiceTest {

    @Mock
    private OrderCreateUserCase orderCreateUserCase;

    @Mock
    private OrderListUserCase orderListUserCase;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderCreateUserCase, orderListUserCase);
    }

    @Test
    void shouldListOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> mockPage = mock(Page.class);
        when(orderListUserCase.execute(pageable)).thenReturn(mockPage);

        // Act
        Page<Order> result = orderService.list(pageable);

        // Assert
        assertNotNull(result);
        verify(orderListUserCase, times(1)).execute(pageable);
    }

    @Test
    void shouldCreateOrder() {
        // Arrange
        Order order = new Order();
        doNothing().when(orderCreateUserCase).execute(order);

        // Act
        orderService.create(order);

        // Assert
        verify(orderCreateUserCase, times(1)).execute(order);
    }
}

