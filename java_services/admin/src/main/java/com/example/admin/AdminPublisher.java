package com.example.admin;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@RestController
public class AdminPublisher {
    @GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot! Admin is running.";
	}

	int count = 0;

	@PostMapping("/send-message-by-admin")
	public ResponseEntity<?> postAllMessage(@RequestBody Object entity) throws Exception, TimeoutException {

		final String queue_name1 = "admin_queue1";
		final String exchange_name = "admin_exchange1";
		final String queue_name2 = "admin_queue2";


		try 
		{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode originalNode = mapper.valueToTree(entity);
        	ObjectNode objectNode = (ObjectNode) originalNode;
			count++;
			objectNode.put("count", count);
        	byte[] messageBytes = mapper.writeValueAsBytes(objectNode);

			channel.exchangeDeclare(exchange_name, "fanout", true, false, null);
			channel.queueDeclare(queue_name1, true, false, false, null);
			channel.queueBind(queue_name1, exchange_name, "");
			channel.queueDeclare(queue_name2, true, false, false, null);
			channel.queueBind(queue_name2, exchange_name, "");
			channel.basicPublish(exchange_name,"",null,messageBytes);

			System.out.println("🚀 Admin Message: " + new String(messageBytes, StandardCharsets.UTF_8));
			JsonNode jsonResponse = mapper.readTree(messageBytes);
        	return ResponseEntity.ok(jsonResponse);

		}catch (Exception e) {
			return ResponseEntity.status(400).body("Error: Invalid input -> "+e.getMessage());
		}

	}

	


		@PostMapping("/send-message-by-admin-007")
	public ResponseEntity<?> postSpecialMessage(@RequestBody Object entity) throws Exception, TimeoutException {

		final String queue_name3 = "admin_queue3";
		final String exchange_name = "admin_exchange2";
		final String routing_key = "admin_routing_key";
		// final String queue_name2 = "admin_queue2";


		try 
		{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode originalNode = mapper.valueToTree(entity);
        	ObjectNode objectNode = (ObjectNode) originalNode;
			count++;
			objectNode.put("count", count);
        	byte[] messageBytes = mapper.writeValueAsBytes(objectNode);

			channel.exchangeDeclare(exchange_name, "direct", true, false, null);
			channel.queueDeclare(queue_name3, true, false, false, null);
			channel.queueBind(queue_name3, exchange_name, routing_key);

			channel.basicPublish(exchange_name,routing_key,null,messageBytes);

			System.out.println("🚀 Admin Message 007: " + new String(messageBytes, StandardCharsets.UTF_8));
			JsonNode jsonResponse = mapper.readTree(messageBytes);
        	return ResponseEntity.ok(jsonResponse);

		}catch (Exception e) {
			return ResponseEntity.status(400).body("Error: Invalid input -> "+e.getMessage());
		}

	}

}
