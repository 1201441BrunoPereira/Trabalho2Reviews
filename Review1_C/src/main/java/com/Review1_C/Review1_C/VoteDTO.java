package com.Review1_C.Review1_C;


import org.springframework.boot.configurationprocessor.json.JSONObject;

public class VoteDTO {

    private String reviewId;

    private boolean vote;

    public VoteDTO(String reviewId, boolean vote) {
        this.reviewId = reviewId;
        this.vote = vote;
    }

    public VoteDTO() {
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

    public static VoteDTO readJson(String json){
        VoteDTO voteDTO = new VoteDTO();
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
