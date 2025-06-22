package com.example.rabbit_mq_java;

import com.rabbitmq.client.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    private static final String QUEUE_NAME = "add-product";

    private Connection connection;
    private Channel channel;
    private boolean consumerStarted = false;

    // 🔸 Root endpoint just says hello
    @GetMapping("/")
    public String home() {
        return "👋 Hello! Use /get-products to start the RabbitMQ consumer.";
    }

    // 🔹 This endpoint starts the RabbitMQ consumer
    @GetMapping("/get-products")
    public String startConsumer() {
        try {
            if (!consumerStarted) {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");

                connection = factory.newConnection();
                channel = connection.createChannel();

                channel.queueDeclare(QUEUE_NAME, true, false, false, null);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println("📥 Received: " + message);

                    // You can parse the JSON here if needed
                    // For now it's just printing the message
                };

                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
                consumerStarted = true;
                // channel.close();
                return "✅ Consumer started. Waiting for messages in 'add-product' queue...";
            } else {
                return "ℹ️ Consumer is already running!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error starting consumer: " + e.getMessage();
        }
    }
}
