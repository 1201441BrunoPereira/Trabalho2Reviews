package com.Review1_C.Review1_C.Interfaces.RabbitMQ;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Component
public class Client {

    @Autowired
    RabbitTemplate rabbitTemplateReview;

    @Autowired
    RabbitTemplate rabbitTemplateProduct;

    @EventListener(ContextRefreshedEvent.class)
    public void initializer(){
        ReceiveReview();
        ReceiveProduct();
    }

    public String ReceiveReview() {
        String message = "Review";
        System.out.println("iniciou execução review");
        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
        Message result = rabbitTemplateReview.sendAndReceive(ClientConfig.RPC_EXCHANGE_REVIEW,ClientConfig.RPC_MESSAGE_QUEUE_REVIEW, newMessage);
        String response = "";
        if (result != null) {
            response = new String(result.getBody());
        }
        System.out.println(response);
        return response;
    }

    public String ReceiveProduct() {
        String message = "Product";
        System.out.println("iniciou execução product");
        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
        Message result = rabbitTemplateProduct.sendAndReceive(ClientConfig.RPC_EXCHANGE_PRODUCT,ClientConfig.RPC_MESSAGE_QUEUE_PRODUCT, newMessage);
        String response = "";
        if (result != null) {
            response = new String(result.getBody());
        }
        System.out.println(response);
        return response;
    }
}
