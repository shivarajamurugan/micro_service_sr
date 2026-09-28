package com.TEK_OrderService.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    private Integer userId;

    private Integer amount;
}