package com.Review1_C.Review1_C.controller;


import com.Review1_C.Review1_C.model.Review;
import com.Review1_C.Review1_C.model.ReviewDTO;
import com.Review1_C.Review1_C.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Review> createReview(@RequestBody final ReviewDTO rev) throws IOException, InterruptedException {
        final Review review = service.create(rev);
        return ResponseEntity.ok(review);
    }


    @PutMapping(value = "/{reviewId}/approve/{reviewStatus}")
    public ResponseEntity<String> approveRejectReview(@PathVariable("reviewId") final Long reviewId, @PathVariable ("reviewStatus") final Boolean reviewStatus) throws IOException, InterruptedException {
        Boolean status = service.approveRejectReview(reviewId,reviewStatus);
        if(!status){
            return ResponseEntity.ok("The review's id you gave it's not associated with a review or this is not in PENDING status");

        }else
            return ResponseEntity.ok("Review's status is changed");
    }

    @DeleteMapping(value = "/{reviewId}/remove")
    public ResponseEntity<String> deleteByReviewId(@PathVariable ("reviewId") final Long reviewId) throws IOException, InterruptedException {
        Boolean deleted = service.deleteReview(reviewId);
        if(deleted) {
            return ResponseEntity.ok("Review deleted");
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Review can't be deleted because have votes or you are note de creator");
    }

    @GetMapping(value = "/status/{reviewId}")
    public String getStatus(@PathVariable ("reviewId") final Long reviewId) throws IOException, InterruptedException {
        return service.getStatus(reviewId);
    }
}
