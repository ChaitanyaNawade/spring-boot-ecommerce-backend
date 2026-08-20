package com.chaitanya.jayganesh.repository;

import com.chaitanya.jayganesh.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>
{

}
