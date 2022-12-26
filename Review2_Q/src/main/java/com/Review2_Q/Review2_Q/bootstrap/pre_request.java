package com.Review2_Q.Review2_Q.bootstrap;


import com.Review2_Q.Review2_Q.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class pre_request {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;


   @EventListener(ContextRefreshedEvent.class)
    public void run() throws JsonProcessingException {
       System.out.println(" [x] Requesting review from recovery system");
       String response = (String) template.convertSendAndReceive(exchange.getName(), "rpc", "Review");
       if(response != null){
           reviewService.updateDataBaseReview(response);
       }
       System.out.println(" [.] Got '" + response + "'");
    }

}
