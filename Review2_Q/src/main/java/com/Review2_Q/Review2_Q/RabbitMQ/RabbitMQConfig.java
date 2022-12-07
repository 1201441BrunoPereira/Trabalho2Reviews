package com.Review2_Q.Review2_Q.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

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
    public FanoutExchange fanoutCreate2() {
        return new FanoutExchange("review2.created");
    }

    @Bean
    public FanoutExchange fanoutDelete2() {
        return new FanoutExchange("review2.deleted");
    }

    @Bean
    public FanoutExchange fanoutChangeStatus2() {
        return new FanoutExchange("review2.changedStatus");
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
    public Queue autoDeleteQueue6() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding4(FanoutExchange fanoutCreate2, Queue autoDeleteQueue4) {
        return BindingBuilder.bind(autoDeleteQueue4).to(fanoutCreate2);
    }

    @Bean
    public Binding binding5(FanoutExchange fanoutDelete2, Queue autoDeleteQueue5) {
        return BindingBuilder.bind(autoDeleteQueue5).to(fanoutDelete2);
    }

    @Bean
    public Binding binding6(FanoutExchange fanoutChangeStatus2, Queue autoDeleteQueue6) {
        return BindingBuilder.bind(autoDeleteQueue6).to(fanoutChangeStatus2);
    }

    @Bean
    public FanoutExchange fanoutVoteCreated() {
        return new FanoutExchange("vote.created");
    }

    @Bean
    public Queue autoDeleteQueue7() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding7(FanoutExchange fanoutVoteCreated, Queue autoDeleteQueue7){
        return BindingBuilder.bind(autoDeleteQueue7).to(fanoutVoteCreated);
    }

}
