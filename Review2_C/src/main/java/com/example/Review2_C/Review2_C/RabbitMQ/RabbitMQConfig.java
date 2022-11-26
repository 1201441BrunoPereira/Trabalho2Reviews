package com.example.Review2_C.Review2_C.RabbitMQ;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange fanoutCreate() {
        return new FanoutExchange("review.created");
    }

    @Bean
    public FanoutExchange fanoutDelete() {
        return new FanoutExchange("review.deleted");
    }

    @Bean
    public FanoutExchange fanoutChangeStatus() {
        return new FanoutExchange("review.changedStatus");
    }
}
