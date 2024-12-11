package com.nttdata.core.order.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.core.order.application.dto.OrderCreateEvent;
import com.nttdata.core.order.application.mapper.OrderMapper;
import com.nttdata.core.order.application.services.OrderService;
import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@EnableKafka
@EmbeddedKafka(partitions = 1, topics = {"orders"})
class OrderConsumerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ObjectMapper objectMapper;

    private OrderConsumer orderConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderConsumer = new OrderConsumer(orderService, objectMapper, orderMapper);
    }

    @Test
    void shouldConsumeOrderEvent() throws Exception {
        // Arrange
        String event = "{\"uuid\": \"8f9452e6-2f8f-4d64-b4be-5d92bf9b9a15\", \"status\": \"PENDING\", \"products\": []}";
        OrderCreateEvent orderCreateEvent = OrderCreateEvent.builder()
                .uuid(UUID.fromString("8f9452e6-2f8f-4d64-b4be-5d92bf9b9a15"))
                .status(Status.PENDING)
                .products(Set.of())
                .build();
        Order order = new Order();

        when(objectMapper.readValue(event, OrderCreateEvent.class)).thenReturn(orderCreateEvent);
        when(orderMapper.toEntity(orderCreateEvent)).thenReturn(order);
        doNothing().when(orderService).create(order);

        // Act
        orderConsumer.consume(event);

        // Assert
        verify(objectMapper, times(1)).readValue(event, OrderCreateEvent.class);
        verify(orderMapper, times(1)).toEntity(orderCreateEvent);
        verify(orderService, times(1)).create(order);
    }

}

