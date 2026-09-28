package com.TEK_OrderService.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private Boolean error;

    private String message;

    private Object data;
}
