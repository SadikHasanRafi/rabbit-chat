package com.example.spring_user2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class User2Consumer {


    @GetMapping("/")
    public String getMethodName() {
        return "user 2 consumer is running successfully";
    }

    @RabbitListener(queues = "common_queue")
    public void receiveMessage(String message) {
        getMessage(message);
    }


    @GetMapping("/get-message")
    public String getMessage(String message) {
        System.out.println("Received message from User 2: " + message);
        return message;
    }

}
