package com.nttdata.core.order.application.services;

import com.nttdata.core.order.application.usecases.OrderCreateUserCase;
import com.nttdata.core.order.application.usecases.OrderListUserCase;
import com.nttdata.core.order.domain.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderCreateUserCase orderCreateUserCase;
    private final OrderListUserCase orderListUserCase;

    public Page<Order> list(Pageable pageable) {
        return orderListUserCase.execute(pageable);
    }

    public void create(Order order) {
        orderCreateUserCase.execute(order);
    }
}
