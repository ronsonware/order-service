package com.nttdata.core.order.application.dto;

import com.nttdata.core.order.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private UUID uuid;
    private Status status;
    private Double price;
    private BigDecimal total;
    private Set<ProductResponse> products;
}
