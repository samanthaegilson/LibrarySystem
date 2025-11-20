package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReviewStarsException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReviewTextException;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import com.google.common.base.Preconditions;

/**
 * A review. Reviews are created by {@link Member} about {@link Media}.
 */
public class Review {
    private final Member member;
    private final Media media;
    private final String text;
    private final int stars;

    /**
     * Invariant properties for Review
     */
    private void checkReview() {
        Preconditions.checkNotNull(member, "Member should never be null.");
        Preconditions.checkNotNull(media, "Media should never be null.");
        Preconditions.checkNotNull(text, "Review text should never be null.");
        Preconditions.checkState(text.length() >= 1, "Review text should have at least 1 symbol.");
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
    private Review(Member member, Media media, String text, int stars) {
        this.member = member;
        this.media = media;
        this.text = text;
        this.stars = stars;
        checkReview();
    }

    /**
     * Builder class for a review
     */
    public static class ReviewBuilder {
        private Member member;
        private Media media;
        private String text;
        private int stars;

        /**
         * Checks that a member for a review is valid
         *
         * @param member the member making the review
         * @return the review builder
         */
        public ReviewBuilder member(Member member) {
            Preconditions.checkNotNull(member, "Member should not be null.");
            this.member = member;
            return this;
        }

        /**
         * Checks that a media for a review is valid
         *
         * @param media the media of the review
         * @return the review builder
         */
        public ReviewBuilder media(Media media) {
            Preconditions.checkNotNull(media, "Media should not be null.");
            this.media = media;
            return this;
        }

        /**
         * Checks that text for a review is valid
         *
         * @param text the text for the review
         * @return the review builder
         * @throws InvalidReviewTextException if the text is empty
         */
        public ReviewBuilder text(String text) throws InvalidReviewTextException {
            Preconditions.checkNotNull(text, "Text should not be null.");
            if (text.isEmpty()) {
                throw new InvalidReviewTextException();
            }
            this.text = text;
            return this;
        }

        /**
         * Checks that the stars for a review are valid
         *
         * @param stars the stars of the review
         * @return the review builder
         * @throws InvalidReviewStarsException if the stars are not between 1 and 5
         */
        public ReviewBuilder stars(int stars) throws InvalidReviewStarsException {
            final int MAX_STARS = 5;
            if (stars < 1 || stars > MAX_STARS) {
                throw new InvalidReviewStarsException();
            }
            this.stars = stars;
            return this;
        }

        /**
         * Creates a review
         *
         * @return the review
         */
        public Review build() {
            return new Review(this.member, this.media, this.text, this.stars);
        }
    }

    public Member getMember() {
        return this.member;
    }

    public Media getMedia() {
        return this.media;
    }

    public String getText() {
        return this.text;
    }

    public int getStars() {
        return this.stars;
    }
}
