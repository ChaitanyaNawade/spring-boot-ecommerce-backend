package com.chaitanya.jayganesh.service;

import com.chaitanya.jayganesh.entity.*;
import com.chaitanya.jayganesh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService
{
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    public String checkOut(String email,String shippingAddress)
    {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(()->new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);


        if(cartItems.isEmpty())
        {
            throw  new RuntimeException("Cart is empty");
        }


        BigDecimal totalAmount = BigDecimal.ZERO;

        for(CartItem item : cartItems)
        {
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        Order order = new Order();

        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PLACED);
        order.setShippingAddress(shippingAddress);
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);


        for(CartItem  item : cartItems)
        {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(item.getProduct().getPrice());

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItems);

        return "order placed successfully";
    }

    public List<Order> getAllOrders()
    {
        return orderRepository.findAll();
    }

    public String updateOrderStatus(Long orderId,OrderStatus status)
    {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("order not found"));

        order.setStatus(status);

        orderRepository.save(order);

        return "order updated";
    }

}
