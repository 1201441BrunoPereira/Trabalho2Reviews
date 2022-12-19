package com.Review2_C.Review2_C.Interfaces.RabbitMQ;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Client {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @EventListener(ContextRefreshedEvent.class)
    public String ReceiveReview() {
        String message = "Review";
        System.out.println("iniciou execução");
        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
        Message result = rabbitTemplate.sendAndReceive(ClientConfig.RPC_EXCHANGE,ClientConfig.RPC_MESSAGE_QUEUE, newMessage);
        String response = "";
        if (result != null) {
            response = new String(result.getBody());
        }
        System.out.println(response);
        return response;
    }
}
