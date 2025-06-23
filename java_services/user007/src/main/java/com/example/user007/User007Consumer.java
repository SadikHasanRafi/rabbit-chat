package com.example.user007;

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
public class User007Consumer {

    @GetMapping("/")
    public String helloString(){
        return "hello from 007";
    }



    @GetMapping("/user007-get-message")
    public Map<String, Object> getMessage() {
        final String[] msg = new String[1];
        msg[0] = "{}"; // default empty JSON
        final String queue_name = "admin_queue3";
        final String exchange_name = "admin_exchange2";
        final String routing_key = "admin_routing_key";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(exchange_name, "direct", true, false, null);
            channel.queueDeclare(queue_name, true, false, false, null);
            channel.queueBind(queue_name, exchange_name, routing_key);


            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                msg[0] = message;
                System.out.println(" 📣 Received user 007: '" + message + "'");
            };

            channel.basicConsume(queue_name, true, deliverCallback, consumerTag -> {
            });

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(msg[0], new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
            });

        } catch (Exception e) {
            return Map.of("error", "Error connecting to RabbitMQ: " + e.getMessage());
        } finally {
            // ? it acts as a subscription
            // try {
            // if (channel != null && channel.isOpen()) {
            // // channel.close();
            // }
            // if (connection != null && connection.isOpen()) {
            // // connection.close();
            // }
            // } catch (IOException | TimeoutException ex) {
            // // ex.printStackTrace();
            // }
        }
    }

}
