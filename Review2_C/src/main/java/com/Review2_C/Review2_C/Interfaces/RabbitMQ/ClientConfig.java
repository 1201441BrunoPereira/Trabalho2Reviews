package com.Review2_C.Review2_C.Interfaces.RabbitMQ;

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
    public static final String RPC_MESSAGE_QUEUE = "rpc_review_recovery";
    public static final String RPC_REPLY_MESSAGE_QUEUE = "rpc_reply_review_recovery";
    public static final String RPC_EXCHANGE = "rpc_review_exchange";

    @Bean
    Queue msgQueue() {

        return new Queue(RPC_MESSAGE_QUEUE);
    }

    @Bean
    Queue replyQueue() {

        return new Queue(RPC_REPLY_MESSAGE_QUEUE);
    }

    @Bean
    TopicExchange exchange() {

        return new TopicExchange(RPC_EXCHANGE);
    }

    @Bean
    Binding msgBinding() {

        return BindingBuilder.bind(msgQueue()).to(exchange()).with(RPC_MESSAGE_QUEUE);
    }

    @Bean
    Binding replyBinding() {

        return BindingBuilder.bind(replyQueue()).to(exchange()).with(RPC_REPLY_MESSAGE_QUEUE);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyAddress(RPC_REPLY_MESSAGE_QUEUE);
        template.setReplyTimeout(10000);
        return template;
    }

    @Bean
    SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RPC_REPLY_MESSAGE_QUEUE);
        container.setMessageListener(rabbitTemplate(connectionFactory));
        return container;
    }
}
