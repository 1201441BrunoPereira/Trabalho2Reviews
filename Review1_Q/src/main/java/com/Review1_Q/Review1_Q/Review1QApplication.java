package com.Review1_Q.Review1_Q;

import com.Review1_Q.Review1_Q.model.Review;
import com.Review1_Q.Review1_Q.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class Review1QApplication {

	@Autowired
	ReviewRepository reviewRepository;
	public static void main(String[] args) {
		SpringApplication.run(Review1QApplication.class, args);
	}


}
