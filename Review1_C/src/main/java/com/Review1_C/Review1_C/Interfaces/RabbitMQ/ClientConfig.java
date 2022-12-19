package com.Review1_C.Review1_C.Interfaces.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
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
    Queue msgQueueProduct() {

        return new Queue(RPC_MESSAGE_QUEUE_PRODUCT);
    }

    @Bean
    Queue replyQueueReview() {

        return new Queue(RPC_REPLY_MESSAGE_QUEUE_REVIEW);
    }

    @Bean
    Queue replyQueueProduct() {

        return new Queue(RPC_REPLY_MESSAGE_QUEUE_PRODUCT);
    }

    @Bean
    TopicExchange exchangeReview() {

        return new TopicExchange(RPC_EXCHANGE_REVIEW);
    }

    @Bean
    TopicExchange exchangeProduct() {

        return new TopicExchange(RPC_EXCHANGE_PRODUCT);
    }

    @Bean
    Binding msgBindingReview() {

        return BindingBuilder.bind(msgQueueReview()).to(exchangeReview()).with(RPC_MESSAGE_QUEUE_REVIEW);
    }

    @Bean
    Binding msgBindingProduct() {

        return BindingBuilder.bind(msgQueueProduct()).to(exchangeProduct()).with(RPC_MESSAGE_QUEUE_PRODUCT);
    }

    @Bean
    Binding replyBindingReview() {

        return BindingBuilder.bind(replyQueueReview()).to(exchangeReview()).with(RPC_REPLY_MESSAGE_QUEUE_REVIEW);
    }

    @Bean
    Binding replyBindingProduct() {

        return BindingBuilder.bind(replyQueueProduct()).to(exchangeProduct()).with(RPC_REPLY_MESSAGE_QUEUE_PRODUCT);
    }

    @Bean
    RabbitTemplate rabbitTemplateReview(ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyAddress(RPC_REPLY_MESSAGE_QUEUE_REVIEW);
        template.setReplyTimeout(10000);
        return template;
    }

    @Bean
    RabbitTemplate rabbitTemplateProduct(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyAddress(RPC_REPLY_MESSAGE_QUEUE_PRODUCT);
        template.setReplyTimeout(10000);
        return template;
    }

    @Bean
    SimpleMessageListenerContainer replyContainerReview(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RPC_REPLY_MESSAGE_QUEUE_REVIEW);
        container.setMessageListener(rabbitTemplateReview(connectionFactory));
        return container;
    }

    @Bean
    SimpleMessageListenerContainer replyContainerProduct(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RPC_REPLY_MESSAGE_QUEUE_PRODUCT);
        container.setMessageListener(rabbitTemplateReview(connectionFactory));
        return container;
    }
}
