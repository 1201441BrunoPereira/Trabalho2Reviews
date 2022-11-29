package com.Review2_Q.Review2_Q.services;

import com.Review2_Q.Review2_Q.model.RatingFrequency;
import com.Review2_Q.Review2_Q.model.Review;
import com.Review2_Q.Review2_Q.repository.ReviewRepository;
import com.Review2_Q.Review2_Q.repository.VoteAndReviewRepository;
import com.Review2_Q.Review2_Q.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private VoteAndReviewRepository voteAndReviewRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Review getReviewById(String reviewId) throws IOException, InterruptedException {
        return repository.getReviewById(reviewId);
    }


    @Override
    public List<Review> getAllReviewsBySku(String sku) throws IOException, InterruptedException {
        return repository.getReviewsByProduct(sku).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reviews not found"));
    }


    @Override
    public List<Review> getAllReviews(){
        return repository.getAllReviews().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reviews not found"));
    }

    @Override
    public List<Review> getAllPendingReviews(){
        return repository.getAllPendingReviews().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no review pending"));
    }

    @Override
    public List<Review> getAllMyReviews(){
        Long userId = Long.valueOf(jwtUtils.getUserFromJwtToken(jwtUtils.getJwt()));
        return repository.getAllMyReviews(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reviews not found"));
    }

    @Override
    public List<Review> getReviewsByProductOrderByVotes(String sku) throws IOException, InterruptedException {
        List<Review> reviewsProduct = repository.getReviewsByProduct(sku).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Review not found"));
        List<Review> reviewsOrderByVote = new ArrayList<>();
        int sizeList = reviewsProduct.size();
        Map<String,Integer> votesByReview = new HashMap<String,Integer>();
        for(int i=0; i<sizeList; i++){
            var votes = voteAndReviewRepository.getTotalVotesByReviewId(reviewsProduct.get(i).getReviewId());
            votesByReview.put(reviewsProduct.get(i).getReviewId(), votes);
        }

        votesByReview = votesByReview.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        String higherVotes;
        String ReviewId;
        for(Map.Entry mapElement : votesByReview.entrySet()){
            higherVotes = mapElement.getKey().toString();
            for(int j=0; j<sizeList; j++){
                ReviewId = reviewsProduct.get(j).getReviewId();
                if(higherVotes.equals(ReviewId)){
                    reviewsOrderByVote.add(reviewsProduct.get(j));
                    break;
                }
            }
        }

        return reviewsOrderByVote;
    }

    @Override
    public RatingFrequency getRatingFrequencyOfProduct(String sku) throws IOException, InterruptedException {

        List<Review> reviews = repository.getReviewsByProduct(sku).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reviews not found"));
        int rating;
        int one=0, two=0, three=0, four=0, five=0;
        for (int i=0; i< reviews.size(); i++){
            rating=reviews.get(i).getRating();
            if (rating == 1){
                one = one + 1;
            }
            else if (rating == 2){
                two = two + 1;
            }
            else if (rating == 3){
                three = three + 1;
            }
            else if (rating == 4){
                four = four + 1;
            }
            else if (rating == 5){
                five = five + 1;
            }
        }
        int some = one+two+three+four+five;
        float globalRating;
        if (some != 0) {
            globalRating = (one + two * 2 + three * 3 + four * 4 + five * 5) / some;
        }else{
            globalRating = 0;
        }
        return new RatingFrequency(one, two, three, four, five, globalRating);
    }

    @Override
    public String getStatus(String reviewId) throws IOException, InterruptedException {
        Review review = repository.getReviewById(reviewId);
        return review.getStatus();
    }

}
