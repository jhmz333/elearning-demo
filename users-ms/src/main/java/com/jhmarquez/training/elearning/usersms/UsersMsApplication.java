package com.jhmarquez.training.elearning.usersms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UsersMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersMsApplication.class, args);
	}

}
