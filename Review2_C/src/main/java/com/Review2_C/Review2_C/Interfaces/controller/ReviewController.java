package com.Review2_C.Review2_C.Interfaces.controller;

import com.Review2_C.Review2_C.model.Review;
import com.Review2_C.Review2_C.model.ReviewDTO;
import com.Review2_C.Review2_C.services.ReviewService;
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
        return new ResponseEntity<>(service.create(rev), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{reviewId}/approve/{reviewStatus}")
    public ResponseEntity<String> approveRejectReview(@PathVariable("reviewId") final String reviewId, @PathVariable ("reviewStatus") final Boolean reviewStatus) throws IOException, InterruptedException {
        Boolean status = service.approveRejectReview(reviewId,reviewStatus);
        if(!status){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The review's id you gave it's not associated with a review or this is not in PENDING status");
        }else
            return ResponseEntity.ok("Review's status is changed");
    }

    @DeleteMapping(value = "/{reviewId}/remove")
    public ResponseEntity<String> deleteByReviewId(@PathVariable ("reviewId") final String reviewId) throws IOException, InterruptedException {
        Boolean deleted = service.deleteReview(reviewId);
        if(deleted) {
            return ResponseEntity.ok("Review deleted");
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Review can't be deleted because have votes or you are note de creator");
    }
}
