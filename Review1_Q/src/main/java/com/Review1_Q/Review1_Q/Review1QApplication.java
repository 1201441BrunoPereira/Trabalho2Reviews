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

	@Bean
	public Queue createReview() {
		return new Queue("CreatedReview1", false);
	}

	@RabbitListener(queues = "CreatedReview1")
	public void listenCreatedReview1(String in) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(in, Review.class);
		reviewRepository.save(rv);
		System.out.println("Message read from myQueue : " + in);
	}

	@Bean
	public Queue deleteReview() {
		return new Queue("DeletedReview1", false);
	}

	@RabbitListener(queues = "DeletedReview1")
	public void listenDeletedReview1(String in) throws JsonProcessingException {
		//ObjectMapper objectMapper = new ObjectMapper();
		//String rv = objectMapper.readValue(in, String);
		Review review = reviewRepository.getReviewById(in.toString());
		reviewRepository.delete(review);
		System.out.println("Message read from myQueue : " + in);
	}

}
