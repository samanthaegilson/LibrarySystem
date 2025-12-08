package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import com.google.common.base.Preconditions;

/**
 * Logic for returning {@link Media}.
 */
public class ReturnMedia {
    private LibrarySystem librarySystem;
    private final Member member;
    private LibrarySystemPersistence persistence;

    /**
     * Checks that ReturnMedia is in a valid state
     */
    private void checkReturnMedia() {
        Preconditions.checkNotNull(member, "Member should not be null.");
    }

    /**
     * A constructor for ReturnMedia. Receives the member
     *
     * @param librarySystem the library system of the media
     * @param member the member returning media
     * @param persistence the persistence of the library system
     */
    public ReturnMedia(LibrarySystem librarySystem, Member member, LibrarySystemPersistence persistence) {
        this.librarySystem = librarySystem;
        this.member = member;
        this.persistence = persistence;
        checkReturnMedia();
    }

    /**
     * Returns the media to the library
     *
     * @param media the media to return
     */
    public void returnMedia(Media media) {
        checkReturnMedia();
        Preconditions.checkNotNull(media, "Media should not be null.");
        this.member.returnMedia(media);
        if (!media.getWaitlist().isEmpty()) {
            // Lets the first person on the waitlist know the media is ready to take out
            Member front = media.getWaitlist().element();
            front.addAnnouncement(media.getTitle());
        }
        this.persistence.saveLibrarySystem(this.librarySystem);
        checkReturnMedia();
    }

    /**
     * Adds a review to a media
     *
     * @param media the media to receive a review
     * @param review the review to add to the media
     */
    public void writeReview(Media media, Review review) {
        checkReturnMedia();
        Preconditions.checkNotNull(media, "Media should not be null.");
        Preconditions.checkNotNull(review, "Review should not be null.");
        media.addReview(review);
        checkReturnMedia();
        Preconditions.checkState(media.getReviews().contains(review), "Review should be added to the media.");
    }
}
