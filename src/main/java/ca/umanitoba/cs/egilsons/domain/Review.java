package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.media.Media;
import com.google.common.base.Preconditions;

/**
 * A review. Reviews are created by {@link Member} about {@link Media}.
 *
 * @param member the author of the review.
 * @param media the media the review is about.
 * @param text the content of the review.
 * @param stars a ranking out of five.
 */
public record Review(Member member, Media media, String text, int stars) {

    /**
     * Invariant properties for Review
     */
    private void checkReview() {
        Preconditions.checkNotNull(member, "Member should never be null.");
        Preconditions.checkNotNull(media, "Media should never be null.");
        Preconditions.checkNotNull(text, "Review text should never be null.");
        Preconditions.checkState(stars > 0 && stars <= 5, "Review should be between 1 and 5 stars");
    }

    /**
     * Constructor for a review. Receives input for the member, media, text and stars
     *
     * @param member the member that created the review
     * @param media the media the review is about
     * @param text the content of the review
     * @param stars the ranking of the review
     */
    public Review(Member member, Media media, String text, int stars) {
        this.member = member;
        this.media = media;
        this.text = text;
        this.stars = stars;
        checkReview();
    }

//    public static class ReviewBuilder {
//        private Member member;
//        private Media media;
//        private String text;
//        private String stars;
//    }
}
