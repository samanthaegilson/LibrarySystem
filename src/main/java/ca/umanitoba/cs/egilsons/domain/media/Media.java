package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Review;

import java.util.List;

/**
 * A piece of media. Media are part of a {@link ca.umanitoba.cs.egilsons.domain.Library}.
 */
public interface Media {
    String getTitle();
    MediaCategory getCategory();
    List<Review> getReviews();
    int getCopies();

    /**
     * Checks if another media is equal to this media
     *
     * @param other the media being compared
     * @return if the two medias are equal or not
     */
    boolean equals(Media other);

    /**
     * Adds a copy of this media
     */
    void addCopy();

    /**
     * Adds a review to the media
     *
     * @param review the review being added
     */
    void addReview(Review review);
}
