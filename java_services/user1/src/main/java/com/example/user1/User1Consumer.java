package com.example.user1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getMessage() {
        final String queue_name = "admin-message-queue";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue_name, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            e.printStackTrace();
            return "Error connecting to RabbitMQ: " + e.getMessage();
        }
        return "Message received successfully.";
    }
}
