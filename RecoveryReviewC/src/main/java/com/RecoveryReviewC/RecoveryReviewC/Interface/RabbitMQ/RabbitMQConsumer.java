package com.RecoveryReviewC.RecoveryReviewC.Interface.RabbitMQ;

import com.RecoveryReviewC.RecoveryReviewC.model.Vote;
import com.RecoveryReviewC.RecoveryReviewC.services.ProductService;
import com.RecoveryReviewC.RecoveryReviewC.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;


    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void consumeJsonMessageToCreate(String review) throws JsonProcessingException {
        reviewService.createReviewByOther(review);
        System.out.println(review);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void consumeJsonMessageToDelete(String review) throws JsonProcessingException {
        reviewService.deleteReviewByOther(review);
        System.out.println(review);
    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void consumeJsonMessageToChange(String review) throws JsonProcessingException {
        reviewService.createReviewByOther(review);
        System.out.println(review);
    }

    @RabbitListener(queues =  "#{autoDeleteQueue4.name}")
    public void consumeJsonMessageToCreateProduct(String product){
        productService.createProduct(product);
        System.out.println(product);
    }

    @RabbitListener(queues =  "#{autoDeleteQueue5.name}")
    public void consumeJsonMessageToUpdateReviewVote(String vote){
        Vote vt = Vote.readJson(vote);
        reviewService.upVote(vt);
        System.out.println(vote);
    }

    @RabbitListener(queues = "reviewRecovery.request")
    public String reviewRecovery(String message) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        if(message.equals("Review")){
            System.out.println(" [x] Received request for review recovery");
            return reviewService.getReviews();
        }else{
            System.out.println(" [x] Received request for product recovery");
            return productService.getProducts();
        }
    }
}
