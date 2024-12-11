package com.nttdata.core.order.application.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductResponse {
    private String name;
    private String description;
    private BigDecimal value;
}
