package com.Review2_Q.Review2_Q.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VoteDTO {

    @Id
    @Column
    private String reviewId;
    @Column
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
}
