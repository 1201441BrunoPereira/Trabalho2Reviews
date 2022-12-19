package com.RecoveryReviewC.RecoveryReviewC.controller.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    public static final String RPC_MESSAGE_QUEUE_REVIEW = "rpc_review_recovery";
    public static final String RPC_REPLY_MESSAGE_QUEUE_REVIEW = "rpc_reply_review_recovery";
    public static final String RPC_EXCHANGE_REVIEW = "rpc_review_exchange";

    public static final String RPC_MESSAGE_QUEUE_PRODUCT = "rpc_product_review_recovery";
    public static final String RPC_REPLY_MESSAGE_QUEUE_PRODUCT = "rpc_reply_product_review_recovery";
    public static final String RPC_EXCHANGE_PRODUCT = "rpc_product_review_exchange";

    @Bean
    Queue msgQueueReview() {
        return new Queue(RPC_MESSAGE_QUEUE_REVIEW);
    }

    @Bean
    Queue replyQueueReview() {
        return new Queue(RPC_REPLY_MESSAGE_QUEUE_REVIEW);
    }

    @Bean
    TopicExchange topicExchangeReview() {
        return new TopicExchange(RPC_EXCHANGE_REVIEW);
    }

    @Bean
    Queue msgQueueProduct() {
        return new Queue(RPC_MESSAGE_QUEUE_PRODUCT);
    }

    @Bean
    Queue replyQueueProduct() {
        return new Queue(RPC_REPLY_MESSAGE_QUEUE_PRODUCT);
    }

    @Bean
    TopicExchange topicExchangeProduct() {
        return new TopicExchange(RPC_EXCHANGE_PRODUCT);
    }

    @Bean
    Binding msgBindingReview() {
        return BindingBuilder.bind(msgQueueReview())
                .to(topicExchangeReview())
                .with(RPC_MESSAGE_QUEUE_REVIEW);
    }

    @Bean
    Binding replyBindingReview() {
        return BindingBuilder.bind(replyQueueReview())
                .to(topicExchangeReview())
                .with(RPC_REPLY_MESSAGE_QUEUE_REVIEW);
    }

    @Bean
    Binding msgBindingProduct() {
        return BindingBuilder.bind(msgQueueProduct())
                .to(topicExchangeProduct())
                .with(RPC_MESSAGE_QUEUE_PRODUCT);
    }

    @Bean
    Binding replyBindingProduct() {
        return BindingBuilder.bind(replyQueueProduct())
                .to(topicExchangeProduct())
                .with(RPC_REPLY_MESSAGE_QUEUE_PRODUCT);
    }
}
