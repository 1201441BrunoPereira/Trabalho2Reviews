package com.Review1_Q.Review1_Q.services;

import com.Review1_Q.Review1_Q.model.RatingFrequency;
import com.Review1_Q.Review1_Q.model.Review;
import com.Review1_Q.Review1_Q.repository.Review2Repository;
import com.Review1_Q.Review1_Q.repository.ReviewRepository;
import com.Review1_Q.Review1_Q.repository.VoteRepository;
import com.Review1_Q.Review1_Q.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private Review2Repository repository2;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Review getReviewById(Long reviewId) throws IOException, InterruptedException {
        Review review = repository.getReviewById(reviewId);
        if(review == null){
            return repository2.getReviewbyId(reviewId);
        }
        return review;
    }

    @Override
    public List<Review> getAllReviewsBySku(String sku) throws IOException, InterruptedException {
        List<Review> reviews = repository.getReviewsByProduct(sku);
        if (reviews.isEmpty()){
            return repository2.getReviewsBySku(sku);
        }
        return reviews;
    }

    @Override
    public List<Review> getAllReviews() throws IOException, InterruptedException {
        List<Review> reviews = repository.getAllReviews();
        if(reviews.isEmpty()){
            return repository2.getAllReviews();
        }
        return reviews;
    }


    @Override
    public List<Review> getAllPendingReviews(){
        return repository.getAllPendingReviews();
    }

    @Override
    public List<Review> getAllMyReviews(){
        Long userId = Long.valueOf(jwtUtils.getUserFromJwtToken(jwtUtils.getJwt()));
        return repository.getAllMyReviews(userId);
    }

    @Override
    public List<Review> getReviewsByProductOrderByVotes(String sku) throws IOException, InterruptedException {
        List<Review> reviewsProduct = repository.getReviewsByProduct(sku);
        if (reviewsProduct.isEmpty()){
            reviewsProduct = repository2.getReviewsBySku(sku);
        }
        List<Review> reviewsOrderByVote = new ArrayList<>();
        int sizeList = reviewsProduct.size();
        Map<Long,Integer> votesByReview = new HashMap<Long,Integer>();
        for(int i=0; i<sizeList; i++){
            var votes = voteRepository.getVotesByReviewId(reviewsProduct.get(i).getReviewId());
            votesByReview.put(reviewsProduct.get(i).getReviewId(), votes);
        }

        votesByReview = votesByReview.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Long higherVotes;
        Long ReviewId;
       for(Map.Entry mapElement : votesByReview.entrySet()){
           higherVotes = (Long) mapElement.getKey();
           for(int j=0; j<sizeList; j++){
               ReviewId = reviewsProduct.get(j).getReviewId();
               if(higherVotes == ReviewId){
                   reviewsOrderByVote.add(reviewsProduct.get(j));
                   break;
               }
           }
       }

        return reviewsOrderByVote;
    }

    @Override
    public RatingFrequency getRatingFrequencyOfProduct(String sku) throws IOException, InterruptedException {

        List<Review> reviews = Stream.concat(repository.getReviewsByProduct(sku).stream(), repository2.getReviewsBySku(sku).stream()).collect(Collectors.toList());
        int rating;
        int one=0, two=0, three=0, four=0, five=0;
        RatingFrequency freq = new RatingFrequency();
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
        float globalRating = repository.getAggregatedRating(sku);
        return new RatingFrequency(one, two, three, four, five, globalRating);
    }

    @Override
    public String getStatus(Long reviewId) throws IOException, InterruptedException {
        Review review = repository.getReviewById(reviewId);
        if(review == null){
           review = repository2.getReviewbyId(reviewId);
        }
        return review.getStatus();
    }

}
