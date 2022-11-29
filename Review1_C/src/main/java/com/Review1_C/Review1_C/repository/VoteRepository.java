package com.Review1_C.Review1_C.repository;

import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class VoteRepository {

    public boolean ReviewIsVoted(String reviewId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8092/getTotalVotesByReview/" + reviewId))
                .build();

        HttpResponse response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            return false;
        }else
            return true;
    }
}

