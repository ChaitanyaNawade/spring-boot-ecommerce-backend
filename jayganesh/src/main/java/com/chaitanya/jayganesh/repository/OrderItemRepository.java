package com.chaitanya.jayganesh.repository;

import com.chaitanya.jayganesh.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>
{

}
