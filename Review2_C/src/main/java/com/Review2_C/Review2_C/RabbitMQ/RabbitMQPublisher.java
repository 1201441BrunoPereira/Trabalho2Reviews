package com.Review2_C.Review2_C.RabbitMQ;

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
    private FanoutExchange fanoutChangeStatus;

    public void sendJsonMessageToCreate(String review){
        template.convertAndSend(fanoutCreate.getName(), "", review);
    }

    public void sendJsonMessageToDelete(String review){
        template.convertAndSend(fanoutDelete.getName(), "", review);
    }

    public void sendJsonMessageToChangeStatus(String review){
        template.convertAndSend(fanoutChangeStatus.getName(), "", review);
    }

}
