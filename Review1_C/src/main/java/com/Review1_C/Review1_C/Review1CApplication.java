package com.Review1_C.Review1_C;

import org.h2.tools.Server;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class Review1CApplication {

	public static void main(String[] args) {
		SpringApplication.run(Review1CApplication.class, args);
	}

	@Bean
	public Queue CreateReview() {
		return new Queue("CreatedReview1", false);
	}

	@Bean
	public Queue DeleteReview() {
		return new Queue("DeletedReview1", false);
	}

	@Bean
	public Queue ChangeStatusReview() {
		return new Queue("ChangedStatusReview1", false);
	}

}
