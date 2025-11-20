package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.media.Media;

/**
 * Logic for returning {@link Media}.
 */
public class ReturnMedia {
    private final Member member;

    /**
     * A constructor for ReturnMedia. Receives the member
     *
     * @param member the member returning media
     */
    public ReturnMedia(Member member) {
        this.member = member;
    }

    /**
     * Returns the media to the library
     *
     * @param media the media to return
     */
    public void returnMedia(Media media) {
        this.member.returnMedia(media);
        if (!media.getWaitlist().isEmpty()) {
            // Lets the first person on the waitlist know the media is ready to take out
            Member front = media.getWaitlist().element();
            front.addAnnouncement(media.getTitle());
        }
    }

    /**
     * Adds a review to a media
     *
     * @param media the media to receive a review
     * @param review the review to add to the media
     */
    public void writeReview(Media media, Review review) {
        media.addReview(review);
    }
}
