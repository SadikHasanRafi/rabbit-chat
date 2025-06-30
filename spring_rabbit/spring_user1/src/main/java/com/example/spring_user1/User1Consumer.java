package com.example.spring_user1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@RestController
public class User1Consumer {
    
    @GetMapping("/")
    public String getMethodName() {
        return "Hello from User1 consumer!";
    }
    
    @RabbitListener(id = "001", queues = "common_queue")
    public void handleMessage(String message) {
        System.out.println("Received message from User 2:: " + message);
    }


    @GetMapping("/get-message")
    public String getMessage(String message) {
        // This method will be called when a message is received from the queue
        System.out.println("Received message: " + message);
        return message;
    }



    @Autowired
    private RabbitListenerEndpointRegistry registry;


    @GetMapping("/consumer1-stop")
    public String stop() {
        registry.getListenerContainer("001").stop();
        System.out.println("Consumer 1 stopped");
        return "Consumer 1 stopped";
    }


    @GetMapping("/consumer1-start")
    public String start() {
        registry.getListenerContainer("001").start();
        System.out.println("Consumer 1 started");
        return "Consumer 1 started";
    }






    @RabbitListener(id = "sp_queue1", queues = "sp_queue1")
    public void receiveMessagesp_queue1(String message) {
        getMessage(message);
    }

    @GetMapping("/consumer1-sp_queue1-start")
    public String startsp_queue1() {
        registry.getListenerContainer("sp_queue1").start();
        System.out.println("Consumer 2 sp_queue1 is started");
        return "Consumer 2 sp_queue1 is started";
    }



    @GetMapping("/consumer1-sp_queue1-stop")
    public String stopsp_queue1() {
        registry.getListenerContainer("sp_queue1").stop();
        System.out.println("Consumer 2 sp_queue1 is stopped");
        return "Consumer 2 sp_queue1 is stopped";
    }




}

