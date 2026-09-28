package com.TEK_OrderService.dto;

import com.TEK_OrderService.entity.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Integer id;

    private Integer userId;

    private Integer amount;

    private OrderStatus status;
}
