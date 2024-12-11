package com.nttdata.core.order.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.core.order.application.dto.OrderCreateEvent;
import com.nttdata.core.order.application.mapper.OrderMapper;
import com.nttdata.core.order.application.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService service;
    private final ObjectMapper objectMapper;
    private final OrderMapper orderMapper;

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void consume(String event) {
        try {
            log.info("Event received: {}", event);
            OrderCreateEvent orderCreateEvent = objectMapper.readValue(event, OrderCreateEvent.class);
            service.create(orderMapper.toEntity(orderCreateEvent));
            log.info("Event processed: {}", event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to process the event", e);
        }
    }

    // TODO create configuration to sent to DLL after reties
}