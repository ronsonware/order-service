package com.nttdata.core.order.application.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.exception.OrderAlreadyExistsException;
import com.nttdata.core.order.domain.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import java.util.UUID;

class OrderCreateUserCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderCreateUserCase orderCreateUserCase;

    private UUID orderUuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderCreateUserCase = new OrderCreateUserCase(orderRepository);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Arrange
        Order order = new Order();
        order.setUuid(orderUuid);
        when(orderRepository.existsByUuid(order.getUuid())).thenReturn(false);

        // Act
        orderCreateUserCase.execute(order);

        // Assert
        verify(orderRepository, times(1)).save(order);
        verify(orderRepository, times(1)).existsByUuid(order.getUuid());
    }

    @Test
    void shouldThrowOrderAlreadyExistsException() {
        // Arrange
        Order order = new Order();
        order.setUuid(orderUuid);
        when(orderRepository.existsByUuid(order.getUuid())).thenReturn(true);

        // Act & Assert
        OrderAlreadyExistsException exception = assertThrows(OrderAlreadyExistsException.class, () -> {
            orderCreateUserCase.execute(order);
        });

        assertEquals("An order with UUID " + orderUuid.toString() + " already exists.", exception.getMessage());
        verify(orderRepository, times(1)).existsByUuid(order.getUuid());
        verify(orderRepository, times(0)).save(order);
    }
}
