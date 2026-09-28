package com.TEK_OrderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TekOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TekOrderServiceApplication.class, args);
	}

}