package com.chaitanya.jayganesh.controller;

import com.chaitanya.jayganesh.dto.CheckOutRequest;
import com.chaitanya.jayganesh.dto.UpdateOrderStatusRequest;
import com.chaitanya.jayganesh.entity.Order;
import com.chaitanya.jayganesh.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController
{

    private final OrderService orderService;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }


    @PostMapping("/checkout")
    public String checkOut(@RequestBody CheckOutRequest checkOutRequest)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return orderService.checkOut(email,checkOutRequest.getShippingAddress());
    }

    @GetMapping("/all")
    public List<Order> getAllOrders()
    {
        return orderService.getAllOrders();
    }

    @PutMapping("/status/{orderId}")
    public String  updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request)
    {
        return orderService.updateOrderStatus(orderId,request.getStatus());
    }
}