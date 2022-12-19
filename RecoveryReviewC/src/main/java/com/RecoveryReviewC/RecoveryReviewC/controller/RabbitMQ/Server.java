package com.RecoveryReviewC.RecoveryReviewC.controller.RabbitMQ;

import com.RecoveryReviewC.RecoveryReviewC.repository.ProductRepository;
import com.RecoveryReviewC.RecoveryReviewC.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Server {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ReviewRepository review;

    @Autowired
    ProductRepository product;


    @RabbitListener(queues = ServerConfig.RPC_MESSAGE_QUEUE_REVIEW)
    public void sendReviews(Message message) throws JsonProcessingException {
        System.out.println("Message: " + message);
        byte[] body = message.getBody();
        //This is the message to be returned by the server
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(review.getAllReviews());
        Message build = MessageBuilder.withBody(json.getBytes()).build();
        System.out.println("Build: " + build);
        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(ServerConfig.RPC_EXCHANGE_REVIEW,ServerConfig.RPC_REPLY_MESSAGE_QUEUE_REVIEW, build, correlationData);
    }

    @RabbitListener(queues = ServerConfig.RPC_REPLY_MESSAGE_QUEUE_PRODUCT)
    public void sendProducts(Message message) throws JsonProcessingException {
        System.out.println("Message: " + message);
        byte[] body = message.getBody();
        //This is the message to be returned by the server
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(product.getAllProducts());
        Message build = MessageBuilder.withBody(json.getBytes()).build();
        System.out.println("Build: " + build);
        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(ServerConfig.RPC_EXCHANGE_REVIEW,ServerConfig.RPC_REPLY_MESSAGE_QUEUE_REVIEW, build, correlationData);
    }
}
