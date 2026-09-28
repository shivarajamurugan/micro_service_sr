package com.TEK_OrderService.client;


import com.TEK_OrderService.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TEK-USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/user/{userId}")
    ResponseDto getUserById(@PathVariable("userId") Integer userId);
}
