package com.example.spring_user6;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class User6Config {
    public static final String EXCHANGE_1 = "sp_exchange1";
    public static final String QUEUE_2 = "sp_queue6";
    public static final String fanout_exchange = "fanout_exchange";
    public static final String direct_exchange = "direct_exchange";

    public static final String common_queue = "common_queue";

    @Bean
    public Queue queue() {
        return new Queue(common_queue, true);
    }

    // @Bean
    // public FanoutExchange fanoutExchange() {
    // return new FanoutExchange(fanout_exchange, true, false);
    // }

    // @Bean
    // public Binding binding1(Queue queue, FanoutExchange fanoutExchange) {
    // return BindingBuilder.bind(queue).to(fanoutExchange);
    // }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(direct_exchange, true, false);
    }

    @Bean
    public Binding binding1(Queue queue, DirectExchange direct_exchange) {
        return BindingBuilder.bind(queue).to(direct_exchange).with(common_queue);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory( ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        
        factory.setPrefetchCount(2); // 👈 This limits to 1 unacked message per consumer
        
        // // Start with 2 workers, max 10
        factory.setConcurrentConsumers(3);
        // factory.setMaxConcurrentConsumers(10);
        
        // // Process orders in batches of 5
        // factory.setBatchSize(2);
        // // factory.setConsumerBatchEnabled(true);
        
        // // Scale up after 10 busy periods
        // factory.setConsecutiveActiveTrigger(10);

        return factory;
    }

}
