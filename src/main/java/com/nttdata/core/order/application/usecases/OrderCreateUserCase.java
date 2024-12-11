package com.nttdata.core.order.application.usecases;

import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.exception.OrderAlreadyExistsException;
import com.nttdata.core.order.domain.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreateUserCase {

    private final OrderRepository repository;

    public void execute(Order order) {
        if (repository.existsByUuid(order.getUuid())) {
            throw new OrderAlreadyExistsException("An order with UUID " + order.getUuid() + " already exists.");
        }

        repository.save(order);
    }
}
