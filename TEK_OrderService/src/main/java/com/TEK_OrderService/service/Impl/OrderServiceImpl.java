package com.TEK_OrderService.service.Impl;

import com.TEK_OrderService.client.resiliance.UserServiceClient;
import com.TEK_OrderService.dto.OrderRequestDto;
import com.TEK_OrderService.dto.OrderResponseDto;
import com.TEK_OrderService.dto.ResponseDto;
import com.TEK_OrderService.entity.Order;
import com.TEK_OrderService.entity.enums.OrderStatus;
import com.TEK_OrderService.repository.OrderRepository;
import com.TEK_OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserServiceClient userServiceClient;


    @Override
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        ResponseDto userResponse = userServiceClient.getUserById(requestDto.getUserId());
        // Check whether user exists
        if (userResponse == null || userResponse.getError()) {
            throw new RuntimeException("User not found");
        }

        Order order = Order.builder().userId(requestDto.getUserId()).amount(requestDto.getAmount()).status(OrderStatus.CREATED).build();

        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    public Page<OrderResponseDto> getOrdersByUserId(Integer userId, Integer orderId, Pageable pageable) {

        // Validate user if userId is provided
        if (userId != null) {

            ResponseDto userResponse = userServiceClient.getUserById(userId);

            if (userResponse == null || userResponse.getError()) {
                throw new RuntimeException("User Service unavailable or user not found");
            }
        }

        // userId + orderId
        if (userId != null && orderId != null) {

            return orderRepository.findByIdAndUserId(orderId, userId, pageable).map(this::mapToResponse);
        }

        // only userId
        if (userId != null) {

            return orderRepository.findByUserId(userId, pageable).map(this::mapToResponse);
        }

        // only orderId
        if (orderId != null) {

            return orderRepository.findById(orderId).map(this::mapToResponse).map(order -> new PageImpl<>(List.of(order), pageable, 1)).orElseGet(() -> new PageImpl<>(Collections.emptyList(), pageable, 0));
        }

        // no filters
        return orderRepository.findAll(pageable).map(this::mapToResponse);
    }

    private OrderResponseDto mapToResponse(Order order) {

        return OrderResponseDto.builder().id(order.getId()).userId(order.getUserId()).amount(order.getAmount()).status(order.getStatus()).build();
    }
}