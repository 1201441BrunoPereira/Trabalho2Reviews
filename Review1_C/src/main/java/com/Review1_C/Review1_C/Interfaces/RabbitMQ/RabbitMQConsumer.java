package com.Review1_C.Review1_C.Interfaces.RabbitMQ;

import com.Review1_C.Review1_C.VoteDTO;
import com.Review1_C.Review1_C.services.ProductService;
import com.Review1_C.Review1_C.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
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
        VoteDTO vt = VoteDTO.readJson(vote);
        reviewService.upVote(vt);
        System.out.println(vote);
    }

    @RabbitListener(queues =  "#{queueTempVote.name}")
    public void consumeJsonMessageToCreateReviewFromTemporaryVote(String tempoVote) throws JsonProcessingException, JSONException {
        reviewService.createReviewByVote(tempoVote);
        System.out.println(tempoVote);
    }
}
