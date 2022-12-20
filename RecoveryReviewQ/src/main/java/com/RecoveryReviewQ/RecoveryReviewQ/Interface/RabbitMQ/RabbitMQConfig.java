package com.RecoveryReviewQ.RecoveryReviewQ.Interface.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange fanoutCreate1() {
        return new FanoutExchange("review.created");
    }

    @Bean
    public FanoutExchange fanoutDelete1() {
        return new FanoutExchange("review.deleted");
    }

    @Bean
    public FanoutExchange fanoutChangeStatus1() {
        return new FanoutExchange("review.changedStatus");
    }

    @Bean
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue autoDeleteQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue autoDeleteQueue3() {
        return new AnonymousQueue();
    }


    @Bean
    public Binding binding1(FanoutExchange fanoutCreate1, Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanoutCreate1);
    }

    @Bean
    public Binding binding2(FanoutExchange fanoutDelete1, Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2).to(fanoutDelete1);
    }

    @Bean
    public Binding binding3(FanoutExchange fanoutChangeStatus1, Queue autoDeleteQueue3) {
        return BindingBuilder.bind(autoDeleteQueue3).to(fanoutChangeStatus1);
    }

    @Bean
    public FanoutExchange fanoutVoteCreated() {
        return new FanoutExchange("vote.created");
    }

    @Bean
    public Queue autoDeleteQueue4() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding4(FanoutExchange fanoutVoteCreated, Queue autoDeleteQueue4){
        return BindingBuilder.bind(autoDeleteQueue4).to(fanoutVoteCreated);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("reviewQRecovery.request");
    }

    @Bean
    public Queue queue() {
        return new Queue("reviewQRecovery.request");
    }

    @Bean
    public Binding binding6(DirectExchange exchange,
                            Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("rpc");
    }


}
