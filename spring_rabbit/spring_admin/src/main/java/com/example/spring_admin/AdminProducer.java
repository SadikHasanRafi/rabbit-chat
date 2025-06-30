package com.example.spring_admin;

import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AdminProducer {
    
    Faker faker = new Faker();
    private final RabbitTemplate rabbitTemplate;
    private static int counter = 1;

    public AdminProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/")
    public String root() {
        return "spring admin producer is running...";
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestBody String entity) {
        // System.out.println("🚀 Received entity: " + entity);

        if (entity == null || entity.trim().isEmpty()) {
            return "❌ Entity cannot be null or empty";
        }

        try {
            float value = Float.parseFloat(entity.trim());
            int sentenceSize = (int) (value * 10000);
            entity = faker.lorem().sentence(sentenceSize);

        } catch (NumberFormatException e) {
            return "❌ Invalid number format in entity: " + entity;
        }

        if (counter == Integer.MAX_VALUE) {
            counter = 1;
        }

        String message = counter++ + ". " + entity;
        rabbitTemplate.convertAndSend(AdminConfig.fanout_exchange, "", message);
        System.out.println("🚀 ~ AdminProducer.java:54 ~ AdminProducer ~ StringsendMessage ~ message : "+ message);
        return "✅ Message sent: " + message;
    }
        



}
