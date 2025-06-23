package com.example.user2;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;




@RestController
public class User2Consumer {
    @GetMapping("/")
    public String getMethodName() {
        return "User 2 is running." ;
    }
    


    @GetMapping("/user2-get-message")
    public Map<String, Object> getMessage() {
    final String[] msg = new String[1];
    msg[0] = "{}"; // default empty JSON
    final String queue_name = "admin_queue2";
    final String exchange_name = "admin_exchange1";

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = null;
    Channel channel = null;

    try {
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchange_name,"fanout",true,false,null);
        channel.queueDeclare(queue_name, true, false, false, null);
        channel.queueBind(queue_name, exchange_name, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            msg[0] = message;
            System.out.println(" 📣 Received user 2 '" + message + "'");
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
