package com.Review1_C.Review1_C.model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;


public class ReviewForVoteDTO {
    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String reviewId;

    @Column
    private boolean isApproved;

    public ReviewForVoteDTO() {

    }

    public ReviewForVoteDTO(String reviewId, boolean isApproved) {
        this.reviewId = reviewId;
        this.isApproved = isApproved;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

}