package com.Review1_Q.Review1_Q;

import com.Review1_Q.Review1_Q.Interfacecs.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Review1QApplication {

	@Autowired
	ReviewRepository reviewRepository;
	public static void main(String[] args) {
		SpringApplication.run(Review1QApplication.class, args);
	}


}
