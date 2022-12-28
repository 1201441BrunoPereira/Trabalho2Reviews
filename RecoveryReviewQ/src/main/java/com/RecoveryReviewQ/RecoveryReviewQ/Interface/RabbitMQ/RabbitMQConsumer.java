package com.RecoveryReviewQ.RecoveryReviewQ.Interface.RabbitMQ;

import com.RecoveryReviewQ.RecoveryReviewQ.model.Vote;
import com.RecoveryReviewQ.RecoveryReviewQ.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Autowired
    private ReviewService reviewService;

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
    public void consumeJsonMessageToUpdateReviewVote(String vote) {
        Vote vt = Vote.readJson(vote);
        reviewService.upVote(vt);
        System.out.println(vote);
    }

    @RabbitListener(queues = "reviewQRecovery.request")
    public String reviewRecovery(int page) throws JsonProcessingException {
        System.out.println(" [x] Received request for review recovery");
        return reviewService.getReviews(page);
    }

}
