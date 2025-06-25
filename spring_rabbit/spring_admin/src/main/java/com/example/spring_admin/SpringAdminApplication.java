package com.example.spring_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAdminApplication {

	public static void main(String[] args) {
		System.out.println("admin");
		SpringApplication.run(SpringAdminApplication.class, args);
	}

}