package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Member;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A piece of media. Media are part of a {@link ca.umanitoba.cs.egilsons.domain.Library}.
 */
public class Media {
    private final String name;
    private final String creator;
    private final MediaFormat format;
    private final MediaCategory category;
    private List<Member> waitlist;
    private List<Review> reviews;
    private int copies;

    /**
     * Invariant properties for Media.
     */
    private void checkMedia() {
        Preconditions.checkNotNull(name, "Name should never be null.");
        Preconditions.checkState(name.length() >= 1,"Media name should have at least 1 symbol.");
        Preconditions.checkNotNull(creator, "Creator should never be null.");
        Preconditions.checkState(creator.length() >= 1,"Creator name should have at least 1 symbol.");
        Preconditions.checkNotNull(format, "Format should never be null.");
        Preconditions.checkNotNull(category, "Category should never be null.");
        Preconditions.checkNotNull(waitlist, "Waitlist should never be null.");
        Preconditions.checkNotNull(reviews, "Reviews should never be null.");
        Preconditions.checkState(copies >= 0, "Copies should never go below 0.");

        for(Member m:waitlist) {
            Preconditions.checkNotNull(m, "Members in waitlist should never be null");
        }

        for (Review r:reviews) {
            Preconditions.checkNotNull(r, "Reviews in reviews should never be null.");
        }
    }

    /**
     * Constructor for media. Takes input for the name, creator, format and category.
     *
     * @param name the name of the media
     * @param creator the creator of the media
     * @param format the format of the media
     * @param category the category or genre of the media
     */
    public Media(String name, String creator, MediaFormat format, MediaCategory category) {
        this.name = name;
        this.creator = creator;
        this.format = format;
        this.category = category;
        this.waitlist = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.copies++;
        checkMedia();
    }

    public String getName() {
        return this.name;
    }

    public String getCreator() {
        return this.creator;
    }

    public MediaFormat getFormat() {
        return this.format;
    }

    public MediaCategory getCategory() {
        return this.category;
    }

    public List<Member> getWaitlist() {
        return this.waitlist;
    }

    public List<Review> getReviews() {
        return this.reviews;
    }

    public int getCopies() {
        return this.copies;
    }

    /**
     * Checks if another media is equal to this media
     *
     * @param other the media being compared
     * @return if the two medias are equal or not
     */
    public boolean equals(Media other) {
        boolean equal = false;
        if (other.name.equals(this.name) && other.creator.equals(this.creator) && other.format == this.format
                && other.category == this.category) {
            equal = true;
        }
        return equal;
    }

    /**
     * Adds a copy of this media
     */
    public void addCopy() {
        this.copies++;
    }

    /**
     * Adds a review to the media
     *
     * @param review the review being added
     */
    public void addReview(Review review) {
        checkMedia();
        this.reviews.add(review);
        checkMedia();
    }
}
