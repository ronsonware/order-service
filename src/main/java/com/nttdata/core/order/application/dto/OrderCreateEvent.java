package com.nttdata.core.order.application.dto;

import com.nttdata.core.order.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateEvent {
    private UUID uuid;
    private Status status;
    private Set<ProductDataEvent> products;
}
