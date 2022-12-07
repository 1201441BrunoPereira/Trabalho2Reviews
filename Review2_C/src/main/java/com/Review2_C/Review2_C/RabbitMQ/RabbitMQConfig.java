package com.Review2_C.Review2_C.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange fanoutCreate() {
        return new FanoutExchange("review2.created");
    }

    @Bean
    public FanoutExchange fanoutCreateForVote() {
        return new FanoutExchange("review2.created.vote");
    }

    @Bean
    public FanoutExchange fanoutDelete() {
        return new FanoutExchange("review2.deleted");
    }

    @Bean
    public FanoutExchange fanoutDeleteForVote() {
        return new FanoutExchange("review2.deleted.vote");
    }

    @Bean
    public FanoutExchange fanoutChangeStatus() {
        return new FanoutExchange("review2.changedStatus");
    }

    @Bean
    public FanoutExchange fanoutChangeStatusForVote() {
        return new FanoutExchange("review1.changedStatus.vote");
    }

    @Bean
    public FanoutExchange fanoutCreate1() {
        return new FanoutExchange("review1.created");
    }

    @Bean
    public FanoutExchange fanoutDelete1() {
        return new FanoutExchange("review1.deleted");
    }

    @Bean
    public FanoutExchange fanoutChangeStatus1() {
        return new FanoutExchange("review1.changedStatus");
    }

    @Bean
    public FanoutExchange fanoutCreateProduct(){
        return new FanoutExchange("product.created.review");
    }

    @Bean
    public FanoutExchange fanoutVoteCreated() {
        return new FanoutExchange("vote.created.review");
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
    public Binding binding4(FanoutExchange fanoutCreateProduct, Queue autoDeleteQueue4){
        return BindingBuilder.bind(autoDeleteQueue4).to(fanoutCreateProduct);
    }
    @Bean
    public Binding binding5(FanoutExchange fanoutVoteCreated, Queue autoDeleteQueue5){
        return BindingBuilder.bind(autoDeleteQueue5).to(fanoutVoteCreated);
    }

}
