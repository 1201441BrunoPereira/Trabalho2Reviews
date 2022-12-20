package com.Review1_C.Review1_C.Interfaces.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("reviewCRecovery.request");
    }
    @Bean
    public FanoutExchange fanoutCreate() {
        return new FanoutExchange("review.created");
    }

    @Bean
    public FanoutExchange fanoutDelete() {
        return new FanoutExchange("review.deleted");
    }

    @Bean
    public FanoutExchange fanoutChangeStatus() {
        return new FanoutExchange("review.changedStatus");
    }

    @Bean
    public FanoutExchange fanoutCreateProduct(){
        return new FanoutExchange("product.created");
    }
    @Bean
    public FanoutExchange fanoutVoteCreated() {
        return new FanoutExchange("vote.created");
    }

    @Bean
    public FanoutExchange fanoutRecovery() {
        return new FanoutExchange("rpc.requests.review.recovery");
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
    public Queue autoDeleteQueue4() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue autoDeleteQueue5() {
        return new AnonymousQueue();
    }


    @Bean
    public Binding binding1(FanoutExchange fanoutCreate, Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanoutCreate);
    }

    @Bean
    public Binding binding2(FanoutExchange fanoutDelete, Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2).to(fanoutDelete);
    }

    @Bean
    public Binding binding3(FanoutExchange fanoutChangeStatus, Queue autoDeleteQueue3) {
        return BindingBuilder.bind(autoDeleteQueue3).to(fanoutChangeStatus);
    }

    @Bean
    public Binding binding4(FanoutExchange fanoutCreateProduct, Queue autoDeleteQueue4){
        return BindingBuilder.bind(autoDeleteQueue4).to(fanoutCreateProduct);
    }

    @Bean
    public Binding binding5(FanoutExchange fanoutVoteCreated, Queue autoDeleteQueue5){
        return BindingBuilder.bind(autoDeleteQueue5).to(fanoutVoteCreated);
    }



}
