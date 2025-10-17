package ca.umanitoba.cs.egilsons.domain;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private final String name;
    private List<Media> currentMedia;
    private List<String> contactInfo;
    private List<String> constraints;
    private List<Review> reviews;

    private void checkMember() {
        Preconditions.checkNotNull(name, "Name should never be null.");
        Preconditions.checkState(name.length() >= 1,"Member name should have at least 1 symbol.");
        Preconditions.checkNotNull(currentMedia, "Current Media should never be null.");
        Preconditions.checkNotNull(contactInfo, "Contact Info should never be null.");
        Preconditions.checkNotNull(constraints, "Constraints should never be null.");
        Preconditions.checkNotNull(reviews, "Reviews should never be null.");

        for (Media m:currentMedia) {
            Preconditions.checkNotNull(m, "Media in currentMedia should never be null.");
        }

        for (Review r:reviews) {
            Preconditions.checkNotNull(r, "Reviews in reviews should never be null.");
        }
    }

    public Member(String name, List<String> contactInfo) {
        this.name = name;
        this.currentMedia = new ArrayList<>();
        this.contactInfo = contactInfo;
        this.constraints = new ArrayList<>();
        this.reviews = new ArrayList<>();
        checkMember();
    }

    public String getName() {
        return this.name;
    }

    public List<Media> getCurrentMedia() {
        return this.currentMedia;
    }

    public List<String> getContactInfo() {
        return this.contactInfo;
    }

    public List<String> getConstraints() {
        return this.constraints;
    }

    public List<Review> getReviews() {
        return this.reviews;
    }

    public void takeOutMedia(Media m) {
        checkMember();
        // return media, not void?
        if (m.takeOut()) {
            currentMedia.add(m);
        }
        checkMember();
    }

    public void returnMedia(Media m) {
        checkMember();
        m.returning();
        currentMedia.remove(m);
        checkMember();
    }

    public void addReview(Review review) {
        checkMember();
        this.reviews.add(review);
        checkMember();
    }

    public void addToMediaWaitlist(Media m) {
        checkMember();
        m.addToWaitlist(this);
        checkMember();
    }

    public void requestMedia(Media media, Library library) {

    }

//    +requestMedia(Media, Library) void
//    +requestResource(Resource) void
}
