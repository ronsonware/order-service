package com.nttdata.core.order.application.usecases;

import com.nttdata.core.order.domain.entities.Order;
import com.nttdata.core.order.domain.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListUserCase {

    private final OrderRepository repository;

    public Page<Order> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
