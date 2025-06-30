package com.example.spring_admin;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;

@Configuration
public class AdminConfig {
    public static final String exchange1 = "sp_exchange1";
    public static final String fanout_exchange = "fanout_exchange";
    public static final String direct_exchange = "direct_exchange";

    public static final String queue1 = "sp_queue1";
    public static final String queue2 = "sp_queue2";
    public static final String queue3 = "sp_queue6";

    public static final String common_queue = "common_queue";

    @Bean
    public Binding binding_fanout1() {
        return BindingBuilder.bind(new Queue(queue2, true))
                .to(new DirectExchange(direct_exchange, true, false)).with("routingKey1");
    }

    @Bean
    public Binding binding_fanout2() {
        return BindingBuilder.bind(new Queue(queue1, true))
                .to(new DirectExchange(direct_exchange, true, false)).with("routingKey2");
    }

    @Bean
    public Binding binding_fanout3() {
        return BindingBuilder.bind(new Queue(queue3, true))
                .to(new DirectExchange(direct_exchange, true, false)).with("routingKey3");
    }

    @Bean
    public Binding binding_direct() {
        return BindingBuilder.bind(new Queue(common_queue, true))
                .to(new DirectExchange(direct_exchange, true, false)).with(common_queue);
    }

}
