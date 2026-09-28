package com.TEK_OrderService.client.resiliance;


import com.TEK_OrderService.client.UserClient;
import com.TEK_OrderService.dto.ResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final UserClient userClient;

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    public ResponseDto getUserById(Integer userId) {

        System.out.println("Calling User Service for userId: " + userId);

        return userClient.getUserById(userId);
    }

    public ResponseDto getUserFallback(Integer userId, Throwable throwable) {

        System.out.println("User Service unavailable for userId: " + userId + ", reason: " + throwable.getMessage());

        return null;
    }
}