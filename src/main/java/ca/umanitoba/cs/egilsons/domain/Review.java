package ca.umanitoba.cs.egilsons.domain;

import com.google.common.base.Preconditions;

public class Review {
    private final Member member;
    private final Media media;
    private String review;
    private int stars;

    private void checkReview() {
        Preconditions.checkNotNull(member, "Member should never be null.");
        Preconditions.checkNotNull(media, "Media should never be null.");
        Preconditions.checkNotNull(review, "Review should never be null.");
        Preconditions.checkState(review.length() >= 1, "Review should be at least 1 symbol.");
        Preconditions.checkState(stars > 0 && stars <= 5, "Review should be between 1 and 5 stars");
    }

    public Review(Member member, Media media, String review, int stars) {
        this.member = member;
        this.media = media;
        this.review = review;
        this.stars = stars;
        checkReview();
    }

    public Member getMember() {
        return this.member;
    }

    public Media getMedia() {
        return this.media;
    }

    public String getReview() {
        return this.review;
    }

    public int getStars() {
        return this.stars;
    }
}
