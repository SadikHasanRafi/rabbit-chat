package com.example.admin;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@RestController
public class AdminPublisher {
    @GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot! Admin is running.";
	}



	@PostMapping("/send-message-by-admin")
	public String postMethodName(@RequestBody Object entity) throws Exception, TimeoutException {
		
		final String queue_name = "admin-message-queue";


		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
			Channel channel = connection.createChannel()) 
		{
			channel.queueDeclare(queue_name, false, false, false, null);
			// String message = "Hello World!";
			byte[] message = SerializationUtils.serialize(entity);
			channel.basicPublish("", queue_name, null, message);
			System.out.println(" [x] Sent '" + message + "'");

		}catch (Exception e) {
			e.printStackTrace();
			return "Error connecting to RabbitMQ: " + e.getMessage();
		}


		// Publish the message
		return entity.toString();
	}

}
