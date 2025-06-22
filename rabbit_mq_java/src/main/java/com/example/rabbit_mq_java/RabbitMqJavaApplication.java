package com.example.rabbit_mq_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMqJavaApplication {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting RabbitMqJavaApplication...");
		SpringApplication.run(RabbitMqJavaApplication.class, args);
	}
}
