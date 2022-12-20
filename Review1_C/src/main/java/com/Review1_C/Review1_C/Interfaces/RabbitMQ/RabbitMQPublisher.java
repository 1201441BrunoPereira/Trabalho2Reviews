package com.Review1_C.Review1_C.Interfaces.RabbitMQ;

import com.Review1_C.Review1_C.model.Review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {


    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanoutCreate;

    @Autowired
    private FanoutExchange fanoutDelete;

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


}
