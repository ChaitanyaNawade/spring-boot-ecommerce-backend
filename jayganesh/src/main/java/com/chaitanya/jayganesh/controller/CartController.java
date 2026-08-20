package com.chaitanya.jayganesh.controller;

import com.chaitanya.jayganesh.dto.AddToCartRequest;
import com.chaitanya.jayganesh.dto.UpdateCartRequest;
import com.chaitanya.jayganesh.entity.Cart;
import com.chaitanya.jayganesh.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController
{

    private final CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }


    @PostMapping("/add")
    public String addtoCart(@RequestBody AddToCartRequest request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return cartService.addToCart(email, request.getProductId(), request.getQuantity());
    }

    @GetMapping
    public Cart getCart()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return  cartService.getCart(email);
    }

    @PutMapping("/update/{cartItemId}")
    public String  updateCartItem(@PathVariable Long cartItemId,@RequestBody UpdateCartRequest request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();


        return cartService.updateCartItem(email,cartItemId, request.getQuantity());
    }

    @DeleteMapping("/remove/{cartItemId}")
    public String deleteCartItem(@PathVariable Long cartItemId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

       return cartService.removeCartItem(email,cartItemId);
    }
}
