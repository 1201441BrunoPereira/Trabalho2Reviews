package com.Review1_C.Review1_C.services;




import com.Review1_C.Review1_C.model.Review;
import com.Review1_C.Review1_C.model.ReviewDTO;

import java.io.IOException;


public interface ReviewService {


    Review create(ReviewDTO rev) throws IOException, InterruptedException;

    Boolean approveRejectReview(Long reviewId, Boolean status) throws IOException, InterruptedException;

    Boolean deleteReview(Long reviewId) throws IOException, InterruptedException;

    String getStatus(Long reviewId) throws IOException, InterruptedException;

}
