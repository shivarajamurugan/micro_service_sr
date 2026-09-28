package com.TEK_UserService.service;

import com.TEK_UserService.dto.UserRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserRequestDto createUser(UserRequestDto userRequestDto);

    Page<UserRequestDto> getAll(String email, Pageable pageable);

    UserRequestDto getById(Integer userId);
}
