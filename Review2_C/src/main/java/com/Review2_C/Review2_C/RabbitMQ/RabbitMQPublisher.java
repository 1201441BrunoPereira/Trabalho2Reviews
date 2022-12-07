package com.Review2_C.Review2_C.RabbitMQ;

import com.Review2_C.Review2_C.model.Review;
import com.Review2_C.Review2_C.model.ReviewForVoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RabbitMQPublisher {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanoutCreate;

    @Autowired
    private FanoutExchange fanoutCreateForVote;

    @Autowired
    private FanoutExchange fanoutDelete;

    @Autowired
    private FanoutExchange fanoutDeleteForVote;

    @Autowired
    private FanoutExchange fanoutChangeStatus;

    @Autowired
    private FanoutExchange fanoutChangeStatusForVote;

    public void sendJsonMessageToCreate(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(review);
        template.convertAndSend(fanoutCreate.getName(), "", json);
    }

    public void sendJsonMessageToCreateForVote(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ReviewForVoteDTO rev = new ReviewForVoteDTO(review.getReviewId(), false);
        String json = ow.writeValueAsString(rev);
        template.convertAndSend(fanoutCreateForVote.getName(), "", json);
    }
    public void sendJsonMessageToDelete(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(review);
        template.convertAndSend(fanoutDelete.getName(), "", json);
    }

    public void sendJsonMessageToDeleteForVote(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ReviewForVoteDTO rev = new ReviewForVoteDTO(review.getReviewId(), false);
        String json = ow.writeValueAsString(rev);
        template.convertAndSend(fanoutDeleteForVote.getName(), "", json);
    }

    public void sendJsonMessageToChangeStatus(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(review);
        template.convertAndSend(fanoutChangeStatus.getName(), "", json);
    }

    public void sendJsonMessageToChangeStatusForVote(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        boolean approved;
        approved = Objects.equals(review.getStatus(), "APPROVED");
        ReviewForVoteDTO rev = new ReviewForVoteDTO(review.getReviewId(), approved);
        String json = ow.writeValueAsString(rev);
        template.convertAndSend(fanoutChangeStatusForVote.getName(), "", json);
    }

}
