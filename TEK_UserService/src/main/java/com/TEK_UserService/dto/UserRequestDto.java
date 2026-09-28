package com.TEK_UserService.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRequestDto {
    private String email;
    private String name;
}
