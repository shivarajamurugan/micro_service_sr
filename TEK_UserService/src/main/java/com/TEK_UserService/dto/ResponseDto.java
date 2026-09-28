package com.TEK_UserService.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseDto {
    private String message;
    private Object data;
    private Boolean error;
}

