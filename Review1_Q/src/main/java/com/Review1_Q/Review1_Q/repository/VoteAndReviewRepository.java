package com.Review1_Q.Review1_Q.repository;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class VoteAndReviewRepository {

    public int getTotalVotesByReviewId(String reviewId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8092/getTotalVotesByReview/" + reviewId))
                .build();

        HttpResponse response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return Integer.parseInt(response.body().toString());
        }else
            return 0;
    }
}

