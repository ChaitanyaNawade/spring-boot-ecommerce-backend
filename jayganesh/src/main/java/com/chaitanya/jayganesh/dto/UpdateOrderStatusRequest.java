package com.chaitanya.jayganesh.dto;

import com.chaitanya.jayganesh.entity.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest
{
    private OrderStatus status;
}
