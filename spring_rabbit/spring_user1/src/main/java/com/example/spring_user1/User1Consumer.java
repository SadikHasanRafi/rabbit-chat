package com.example.spring_user1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@RestController
public class User1Consumer {
    
    @GetMapping("/")
    public String getMethodName() {
        return "Hello from User1 consumer!";
    }
    
    @RabbitListener(queues = "common_queue")
    public void handleMessage(String message) {
        getMessage("📧 Email received: " + message);
    }


    @GetMapping("/get-message")
    public String getMessage(String message) {
        // This method will be called when a message is received from the queue
        System.out.println("Received message: " + message);
        return message;
    }
}

