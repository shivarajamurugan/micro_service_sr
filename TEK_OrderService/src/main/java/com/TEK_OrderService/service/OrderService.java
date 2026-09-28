package com.TEK_OrderService.service;

import com.TEK_OrderService.dto.OrderRequestDto;
import com.TEK_OrderService.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto requestDto);

    Page<OrderResponseDto> getOrdersByUserId(Integer userId,Integer orderId, Pageable pageable);
}
