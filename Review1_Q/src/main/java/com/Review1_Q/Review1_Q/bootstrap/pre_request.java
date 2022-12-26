package com.Review1_Q.Review1_Q.bootstrap;


import com.Review1_Q.Review1_Q.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class pre_request {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    int page = 0;
    String response;

   @EventListener(ContextRefreshedEvent.class)
    public void run() throws JsonProcessingException {
       System.out.println(" [x] Requesting review from recovery system");
       do {
           response = (String) template.convertSendAndReceive(exchange.getName(), "rpc", page);
           if(response != null){
               reviewService.updateDataBaseReview(response);
           }
           System.out.println(" [.] Got '" + response + "'");
           page++;
       }while (!Objects.equals(response, "[ ]"));
    }

}
