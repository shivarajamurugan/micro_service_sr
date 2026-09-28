package com.TEK_UserService.service.impl;

import com.TEK_UserService.dto.UserRequestDto;
import com.TEK_UserService.entity.User;
import com.TEK_UserService.repository.UserRepository;
import com.TEK_UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserRequestDto createUser(UserRequestDto userRequestDto) {
        userRepository.save(User.builder()
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .build());
        return userRequestDto;
    }

    @Override
    public Page<UserRequestDto> getAll(String email, Pageable pageable) {

        Page<User> users;

        if (email == null || email.isBlank()) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findByEmailContainingIgnoreCase(email, pageable);
        }

        return users.map(user -> UserRequestDto
                .builder()
                .email(user.getEmail())
                .name(user.getName())
                .build());
    }

    @Override
    public UserRequestDto getById(Integer userId) {

        User user = userRepository.getById(userId);

        return UserRequestDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();

    }
}
