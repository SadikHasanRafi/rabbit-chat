package com.example.spring_user6;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class User6Consumer {
    

    @GetMapping("/")
    public String getMethodName() {
        return "hellow this is user6 consumer!!!";
    }


    @RabbitListener(queues = "common_queue")
    public void receiveMessage(String message) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Received message from common_queue: " + message);
    }


    @GetMapping("/get-message")
    public String getMessage() {
        return "Hello, this is user6 consumer!!!";
    }

}
