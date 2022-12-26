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

import java.util.Objects;

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

    int page = 0;
    int productPage = 0;
    String response;
    String responseProduct;


   @EventListener(ContextRefreshedEvent.class)
    public void run() throws JsonProcessingException {
       System.out.println(" [x] Requesting review from recovery system");
       do {
           String pageString = String.valueOf(page);
           response = (String) template.convertSendAndReceive(exchange.getName(), "rpc", "Review"+pageString);
           if(response != null ){
               reviewService.updateDataBaseReview(response);
           }
           System.out.println(" [.] Got '" + response + "'");
           page++;
       }while (!Objects.equals(response, "[ ]"));

           System.out.println(" [x] Requesting product from recovery system");
       do{
           String pageString = String.valueOf(page);
           responseProduct = (String) template.convertSendAndReceive(exchange.getName(), "rpc", "Produc"+pageString );
           if(responseProduct != null) {
               productService.updateDataBaseProduct(responseProduct);
           }
           System.out.println(" [.] Got '" + responseProduct + "'");
           productPage++;
       }while (!Objects.equals(response, "[ ]"));
    }
}
