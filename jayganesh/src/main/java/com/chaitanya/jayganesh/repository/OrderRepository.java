package com.chaitanya.jayganesh.repository;

import com.chaitanya.jayganesh.entity.Order;
import com.chaitanya.jayganesh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long>
{
    List<Order> findByUser(User user);
}


