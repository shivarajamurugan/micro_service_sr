package com.TEK_OrderService.repository;


import com.TEK_OrderService.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByUserId(Integer userId, Pageable pageable);

    Page<Order> findById(Integer orderId, Pageable pageable);

    Page<Order> findByIdAndUserId(Integer orderId, Integer userId, Pageable pageable);

}