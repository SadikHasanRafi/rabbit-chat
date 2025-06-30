package com.example.spring_user6;

import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class User6Consumer {
    

    @Autowired
    private RabbitListenerEndpointRegistry registry;









    @GetMapping("/")
    public String getMethodName() {
        registry.getListenerContainer("006").start();
        return "hellow this is user6 consumer!!!";
    }


    @RabbitListener(id="006", queues = "common_queue",containerFactory = "rabbitListenerContainerFactory" , autoStartup = "false" )
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        int x = (int) (5);
        checkData(message, x);
        // channel.basicQos(2);
       
        // System.out.println("Received message from common_queue: " + message);
    }

int count = 1;


    public void checkData(String message, int x){
        System.out.print(count++ + " 🏁🏁 Start Processing message. " );
         try {
            Thread.sleep(1000*x);
            // Thread.sleep(1000);
            System.out.println("🚀 Done with message: " + message + " 🎉");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @GetMapping("/stop")
    public String stopListener() {
        registry.getListenerContainer("006").stop();
        return "🔴 Listener stopped successfully!";
    }

    @GetMapping("/start")
    public String startListener() {
        registry.getListenerContainer("006").start();
        return "🟢 Listener started successfully!";
    }

    @GetMapping("/is-running")
    public String getMessage() {
        if(registry.getListenerContainer("006").isRunning()) {
            return "Listener is running! 🟢";
        } else {
            return "Listener is not running! 🔴";
        }
    }

    

}
