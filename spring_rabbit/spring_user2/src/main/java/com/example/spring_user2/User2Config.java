package com.example.spring_user2;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class User2Config {

    public static final String EXCHANGE_1 = "sp_exchange1";
    public static final String QUEUE_2 = "sp_queue2";
    public static final String fanout_exchange = "fanout_exchange";
    public static final String direct_exchange = "direct_exchange";
    public static final String common_queue = "common_queue";

    @Bean
    public Queue queue() {
        return new Queue(common_queue, true);
    }

    // @Bean
    // public FanoutExchange fanoutExchange() {
    //     return new FanoutExchange(fanout_exchange, true, false);
    // }

    // @Bean
    // public Binding binding1(Queue queue, FanoutExchange fanoutExchange) {
    //     return BindingBuilder.bind(queue).to(fanoutExchange);
    // }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(direct_exchange, true, false);
    }

    @Bean
    public Binding binding1(Queue queue, DirectExchange direct_exchange) {
        return BindingBuilder.bind(queue).to(direct_exchange).with(common_queue); 
    }

}
