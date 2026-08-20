package com.chaitanya.jayganesh.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest
{
    private BigDecimal amount;
}
