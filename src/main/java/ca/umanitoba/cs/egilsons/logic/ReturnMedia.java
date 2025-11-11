package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.media.Media;

public class ReturnMedia {
    private final Library library;
    private final Member member;

    public ReturnMedia(Library library, Member member) {
        this.library = library;
        this.member = member;
    }

    public void returnMedia(Media media) {
        this.member.returnMedia(media);
        if (!media.getWaitlist().isEmpty()) {
            Member front = media.getWaitlist().remove();
            front.addAnnouncement(media.getTitle());
        }
    }

    public void writeReview(Media media, Review review) {
        media.addReview(review);
    }
}
