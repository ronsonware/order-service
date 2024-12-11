package com.nttdata.core.order.application.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import java.util.List;

class OrderListUserCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderListUserCase orderListUserCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderListUserCase = new OrderListUserCase(orderRepository);
    }

    @Test
    void shouldReturnPageOfOrders() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Order order = new Order();
        Page<Order> page = new PageImpl<>(List.of(order));

        when(orderRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<Order> result = orderListUserCase.execute(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(orderRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnEmptyPageWhenNoOrders() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Page<Order> page = new PageImpl<>(List.of());

        when(orderRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<Order> result = orderListUserCase.execute(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(orderRepository, times(1)).findAll(pageable);
    }
}

