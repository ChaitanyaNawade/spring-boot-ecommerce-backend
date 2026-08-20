package com.chaitanya.jayganesh.service;

import com.chaitanya.jayganesh.entity.Cart;
import com.chaitanya.jayganesh.entity.CartItem;
import com.chaitanya.jayganesh.entity.Product;
import com.chaitanya.jayganesh.entity.User;
import com.chaitanya.jayganesh.repository.CartItemRepository;
import com.chaitanya.jayganesh.repository.CartRepository;
import com.chaitanya.jayganesh.repository.ProductRepository;
import com.chaitanya.jayganesh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public String addToCart(String email,Long productId,int quantity)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("product not found"));


        Cart cart = cartRepository.findByUser(user)
                .orElseGet(()->
                {
                    Cart  newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart,product);

        if(existingItem.isPresent())
        {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity()+quantity);
            cartItemRepository.save(item);
        }
        else
        {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }

        return "product added to cart";

    }


    public Cart getCart(String email)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(()->new RuntimeException("cart not found"));

        return cart;
    }


    public String updateCartItem(String email,Long cartItemId,int quantity)
    {
        CartItem item = cartItemRepository.findById(cartItemId).
                orElseThrow(()->new RuntimeException("cart item not found"));


        if(!item.getCart().getUser().getEmail().equals(email))
        {
            throw new RuntimeException("you are not allowed to update the cart");
        }
        else
        {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return "cart successfully updated";
    }


    public String removeCartItem(String email,Long cartItemId)
    {
       CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new RuntimeException("cart item not found"));

       if(!item.getCart().getUser().getEmail().equals(email))
       {
           throw new RuntimeException("you are not allowed to remove this cart");
       }
       else
       {
           cartItemRepository.deleteById(cartItemId);
       }

       return "cartitem removed successfully";
    }
}
