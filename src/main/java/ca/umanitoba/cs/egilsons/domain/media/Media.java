package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.map.Coordinates;

import java.util.List;
import java.util.Queue;

/**
 * A piece of media. Media are part of a {@link ca.umanitoba.cs.egilsons.domain.Library}.
 */
public interface Media {
    String getTitle();
    MediaCategory getCategory();
    List<Review> getReviews();
    int getCopies();
    Coordinates getCoordinates();
    Queue<Member> getWaitlist();

    /**
     * Checks if a member is at the front of the waitlist
     *
     * @param member the member being checked
     * @return if the member is at the front or not
     */
    boolean frontOfWaitlist(Member member);

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
     * Removes a copy of this media if a copy is available
     *
     * @return a copy of the media
     */
    Media takeOutCopy();

    /**
     * Adds a member to the waitlist
     *
     * @param member the member to be added
     * @return the spot in the waitlist
     */
    int addToWaitlist(Member member);

    /**
     * Adds a review to the media
     *
     * @param review the review being added
     */
    void addReview(Review review);
}
