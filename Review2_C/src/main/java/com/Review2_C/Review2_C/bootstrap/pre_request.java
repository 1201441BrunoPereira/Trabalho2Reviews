package com.Review2_C.Review2_C.bootstrap;


import com.Review2_C.Review2_C.services.ProductService;
import com.Review2_C.Review2_C.services.ReviewService;
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
    private ProductService productService;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    //int start = 0;

   @EventListener(ContextRefreshedEvent.class)
    public void run() throws JsonProcessingException {
       System.out.println(" [x] Requesting review from recovery system");
       String response = (String) template.convertSendAndReceive(exchange.getName(), "rpc", "Review");
       reviewService.updateDataBaseReview(response);
       System.out.println(" [.] Got '" + response + "'");
       System.out.println(" [x] Requesting product from recovery system");
       String responseProduct = (String) template.convertSendAndReceive(exchange.getName(), "rpc", "Product");
       productService.updateDataBaseProduct(responseProduct);
       System.out.println(" [.] Got '" + responseProduct + "'");
    }

    /*@Override
    public void run(String... args) throws Exception {
        System.out.println(" [x] Requesting review from recovery system(" + start + ")");
        String response = (String) template.convertSendAndReceive(exchange.getName(), "rpc", "Review");
        System.out.println(" [.] Got '" + response + "'");
        System.out.println(" [x] Requesting product from recovery system(" + start + ")");
        String responseProduct = (String) template.convertSendAndReceive(exchange.getName(), "rpc", "Product");
        System.out.println(" [.] Got '" + responseProduct + "'");
    }*/
}
