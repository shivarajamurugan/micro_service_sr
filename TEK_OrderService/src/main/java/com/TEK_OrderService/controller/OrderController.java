package com.TEK_OrderService.controller;

import com.TEK_OrderService.dto.OrderRequestDto;
import com.TEK_OrderService.dto.ResponseDto;
import com.TEK_OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController{

    private final OrderService orderService;

    @PostMapping
    public ResponseDto createOrder(@RequestBody OrderRequestDto requestDto) {

        return ResponseDto.builder()
                .error(false)
                .message("Order created successfully")
                .data(orderService.createOrder(requestDto))
                .build();
    }

    @GetMapping
    public ResponseDto getOrders(@RequestParam(required = false) Integer userId,@RequestParam(required = false) Integer orderId,
                                 @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseDto.builder()
                .error(false)
                .message("Orders fetched successfully")
                .data(orderService.getOrdersByUserId(userId, orderId, pageable))
                .build();
    }
}

