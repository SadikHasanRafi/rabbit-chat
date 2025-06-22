package com.example.user1;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@RestController
public class User1Consumer {
    @GetMapping("/")
	public String index() {
		return " User 1 is running.";
	}

    @GetMapping("/user1-get-message")
    // public Map<String, Object> getMessage() {
    //     final String[] msg = new String[1];
    //     msg[0] = "{}"; // default empty JSON
    //     final String queue_name = "admin-message-queue";

    //     ConnectionFactory factory = new ConnectionFactory();
    //     factory.setHost("localhost");

    //     try (Connection connection = factory.newConnection();
    //          Channel channel = connection.createChannel()) {

    //         channel.queueDeclare(queue_name, false, false, false, null);

    //         DeliverCallback deliverCallback = (consumerTag, delivery) -> {
    //             String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
    //             msg[0] = message;
    //             System.out.println(" [x] Received '" + message + "'");
    //         };

    //         channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> { });

    //         // Small delay to wait for message delivery (not ideal for production)
    //         Thread.sleep(500);

    //         // Parse JSON string into a Map
    //         ObjectMapper mapper = new ObjectMapper();
    //         return mapper.readValue(msg[0], Map.class);

    //     } catch (Exception e) {
    //         return Map.of("error", "Error connecting to RabbitMQ: " + e.getMessage());
    //     }
    // }
    @SuppressWarnings("UseSpecificCatch")
public Map<String, Object> getMessage() {
    final String[] msg = new String[1];
    msg[0] = "{}"; // default empty JSON
    final String queue_name = "admin-message-queue";

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = null;
    Channel channel = null;

    try {
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(queue_name, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            msg[0] = message;
            System.out.println(" 📣 Received '" + message + "'");
        };

        channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> { });


        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(msg[0], new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});

    } catch (Exception e) {
        return Map.of("error", "Error connecting to RabbitMQ: " + e.getMessage());
    } finally {
        //? it acts as a subscription  
        // try {
        //     if (channel != null && channel.isOpen()) {
        //         // channel.close();
        //     }
        //     if (connection != null && connection.isOpen()) {
        //         // connection.close();
        //     }
        // } catch (IOException | TimeoutException ex) {
        //     // ex.printStackTrace();
        // }
    }
}



}
