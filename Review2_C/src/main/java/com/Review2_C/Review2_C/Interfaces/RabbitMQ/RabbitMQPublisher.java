package com.Review2_C.Review2_C.Interfaces.RabbitMQ;

import com.Review2_C.Review2_C.model.Review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanoutCreate;

    @Autowired
    private FanoutExchange fanoutDelete;

    @Autowired
    private FanoutExchange fanoutDeleteVote;

    @Autowired
    private FanoutExchange fanoutChangeStatus;


    public void sendJsonMessageToCreate(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(review);
        template.convertAndSend(fanoutCreate.getName(), "", json);
    }

    public void sendJsonMessageToDelete(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(review);
        template.convertAndSend(fanoutDelete.getName(), "", json);
    }

    public void sendJsonMessageToChangeStatus(Review review) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(review);
        template.convertAndSend(fanoutChangeStatus.getName(), "", json);
    }

    public void sendJsonMessageToDeleteTempVote(String voteIdIfCreatedFromVote) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(voteIdIfCreatedFromVote);
        template.convertAndSend(fanoutDeleteVote.getName(), "", json);
    }


}
