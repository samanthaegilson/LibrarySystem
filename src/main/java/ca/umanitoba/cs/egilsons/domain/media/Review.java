package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Member;
import com.google.common.base.Preconditions;

/**
 * A review. Reviews are created by {@link Member} about {@link Media}.
 *
 * @param member the author of the review.
 * @param media the media the review is about.
 * @param review the content of the review.
 * @param stars a ranking out of five.
 */
public record Review(Member member, Media media, String review, int stars) {

    /**
     * Invariant properties for Review
     */
    private void checkReview() {
        Preconditions.checkNotNull(member, "Member should never be null.");
        Preconditions.checkNotNull(media, "Media should never be null.");
        Preconditions.checkNotNull(review, "Review should never be null.");
        Preconditions.checkState(review.length() >= 1, "Review should be at least 1 symbol.");
        Preconditions.checkState(stars > 0 && stars <= 5, "Review should be between 1 and 5 stars");
    }

    /**
     * Constructor for a review. Recieves input for the member, media, review text and stars
     *
     * @param member the member that created the review
     * @param media the media the review is about
     * @param review the content of the review
     * @param stars the ranking of the review
     */
    public Review(Member member, Media media, String review, int stars) {
        this.member = member;
        this.media = media;
        this.review = review;
        this.stars = stars;
        checkReview();
    }
}
