package com.nttdata.core.order.domain.repositories;

import com.nttdata.core.order.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByUuid(UUID uuid);
}
