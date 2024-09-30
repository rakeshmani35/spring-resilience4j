package com.resilience4J.catalog.service.api.repository;

import com.resilience4J.catalog.service.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCategory(String category);


}
