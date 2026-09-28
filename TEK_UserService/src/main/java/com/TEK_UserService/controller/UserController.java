package com.TEK_UserService.controller;

import com.TEK_UserService.dto.ResponseDto;
import com.TEK_UserService.dto.UserRequestDto;
import com.TEK_UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseDto.builder()
                .error(false)
                .message("user created successfully")
                .data(userService.createUser(userRequestDto))
                .build();
    }

    @GetMapping
    public ResponseDto getAll(@RequestParam(required = false, name = "email") String email, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseDto.builder()
                .error(false)
                .message("Users fetched successfully")
                .data(userService.getAll(email, pageable))
                .build();
    }

    @GetMapping("/{usedId}")
    public ResponseDto getById(@PathVariable Integer userId){
        return ResponseDto.builder()
                .error(false)
                .message("Users fetched successfully")
                .data(userService.getById(userId))
                .build();
    }
}
