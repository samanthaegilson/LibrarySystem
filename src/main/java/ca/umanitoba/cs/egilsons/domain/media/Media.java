package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Review;

import java.util.List;

/**
 * A piece of media. Media are part of a {@link ca.umanitoba.cs.egilsons.domain.Library}.
 */
public interface Media {
    public String getTitle();
    public MediaCategory getCategory();
    public List<Review> getReviews();
    public int getCopies();

    /**
     * Checks if another media is equal to this media
     *
     * @param other the media being compared
     * @return if the two medias are equal or not
     */
    public boolean equals(Media other);

    /**
     * Adds a copy of this media
     */
    public void addCopy();

    /**
     * Adds a review to the media
     *
     * @param review the review being added
     */
    public void addReview(Review review);
}
