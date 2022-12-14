package com.RecoveryReviewC.RecoveryReviewC.model;


import org.springframework.boot.configurationprocessor.json.JSONObject;

public class Vote {

    private String reviewId;

    private boolean vote;

    public Vote(String reviewId, boolean vote) {
        this.reviewId = reviewId;
        this.vote = vote;
    }

    public Vote() {
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public static Vote readJson(String json){
        Vote voteDTO = new Vote();
        try{

            JSONObject object = new JSONObject(json);
            String reviewId = object.getString("reviewId");
            boolean vote = object.getBoolean("vote");
            voteDTO.setReviewId(reviewId);
            voteDTO.setVote(vote);

        }catch(Exception e) {
            System.out.println("Error in Result as " + e.toString());
        }

        return voteDTO;
    }
}
