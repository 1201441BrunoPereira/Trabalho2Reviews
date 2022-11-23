package com.Review1_Q.Review1_Q.repository;


import com.Review1_Q.Review1_Q.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;


@Repository
public class Review2Repository {

    public Review getReviewById(String reviewId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8086/reviews/internal/" + reviewId))
                .build();

        HttpResponse response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        var code = response.statusCode();
        if(code == 200){
            ObjectMapper objectMapper = new ObjectMapper();
            String body = response.body().toString();
            return objectMapper.readValue(body,Review.class);
        }
        return null;
    }

    public List<Review> getReviewsBySku(String sku) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8086/reviews/internal/"+sku+"/product"))
                .build();

        HttpResponse response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        String body = response.body().toString();
        List<Review> myObjects = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, Review.class));
        if(myObjects==null){
            myObjects = Collections.emptyList();
        }
        return myObjects;
    }

}