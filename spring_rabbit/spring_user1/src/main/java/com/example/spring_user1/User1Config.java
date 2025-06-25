package com.example.spring_user1;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Removed incorrect import of com.rabbitmq.client.AMQP.Queue
import org.springframework.amqp.core.Queue;


@Configuration
public class User1Config {
    public static final String exchange1 = "sp_exchange1";
    public static final String fanout_exchange = "fanout_exchange";
    public static final String direct_exchange = "direct_exchange";
    public static final String common_queue = "common_queue";

    public static final String queue1 = "sp_queue1";


   @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(common_queue);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanout_exchange, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(common_queue, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(common_queue);
    }

}
