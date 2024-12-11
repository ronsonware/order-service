package com.nttdata.core.order.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDataEvent {
    private String name;
    private String description;
    private BigDecimal value;
}
