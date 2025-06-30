package com.example.spring_user2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class User2Consumer {


    @GetMapping("/")
    public String getMethodName() {
        return "user 2 consumer is running successfully";
    }

    @Autowired
    private RabbitListenerEndpointRegistry registry;

    @RabbitListener(id = "002", queues = "common_queue")
    public void receiveMessage(String message) {
        System.out.println("Received message from User 2: " + message);
    }




    @GetMapping("/get-message")
    public String getMessage(String message) {
        System.out.println("Received message from User 2: " + message);
        return message;
    }


    @GetMapping("/consumer2-stop")
    public String stop() {
        registry.getListenerContainer("002").stop();
        System.out.println("Consumer 2 stopped");
        return "Consumer 2 stopped";
    }


    @GetMapping("/consumer2-start")
    public String start() {
        registry.getListenerContainer("002").start();
        System.out.println("Consumer 2 started");
        return "Consumer 2 started";
    }




    

    @RabbitListener(id = "sp_queue2", queues = "sp_queue2")
    public void receiveMessagesp_queue2(String message) {
        getMessage(message);
    }

    @GetMapping("/consumer2-sp_queue2-start")
    public String startsp_queue2() {
        registry.getListenerContainer("sp_queue2").start();
        System.out.println("Consumer 2 sp_queue2 is started");
        return "Consumer 2 sp_queue2 is started";
    }



    @GetMapping("/consumer2-sp_queue2-stop")
    public String stopsp_queue2() {
        registry.getListenerContainer("sp_queue2").stop();
        System.out.println("Consumer 2 sp_queue2 is stopped");
        return "Consumer 2 sp_queue2 is stopped";
    }


    
    

}
