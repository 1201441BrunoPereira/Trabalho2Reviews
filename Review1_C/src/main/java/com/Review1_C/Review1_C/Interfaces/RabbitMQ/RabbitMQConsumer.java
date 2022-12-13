package com.Review1_C.Review1_C.Interfaces.RabbitMQ;

import com.Review1_C.Review1_C.Interfaces.repository.ProductRepository;
import com.Review1_C.Review1_C.Interfaces.repository.ReviewRepository;
import com.Review1_C.Review1_C.model.ProductDTO;
import com.Review1_C.Review1_C.model.Review;
import com.Review1_C.Review1_C.model.VoteDTO;
import com.Review1_C.Review1_C.services.ProductService;
import com.Review1_C.Review1_C.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

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


}
