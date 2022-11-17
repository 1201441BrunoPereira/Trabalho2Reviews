package com.Review1_C.Review1_C.services;




import com.Review1_C.Review1_C.model.Review;
import com.Review1_C.Review1_C.model.ReviewDTO;

import java.io.IOException;
import java.util.UUID;


public interface ReviewService {


    Review create(ReviewDTO rev) throws IOException, InterruptedException;

    Boolean approveRejectReview(String reviewId, Boolean status) throws IOException, InterruptedException;

    Boolean deleteReview(String reviewId) throws IOException, InterruptedException;

}
