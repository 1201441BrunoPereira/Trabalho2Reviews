package com.RecoveryReviewC.RecoveryReviewC.controller.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;

public class RabbitMQPublisher {


    /*@RabbitListener(queues = "rpc.requests.review.recovery")
    //@SendTo("tut.rpc.replies")
    public String sendReviewRecovery() {
        System.out.println(" Received request for ");
        System.out.println(" Returned Teste" );
        return "Teste";
    }*/

}
