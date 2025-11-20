package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidDVDRunTimeException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidDirectorException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidTitleException;
import ca.umanitoba.cs.egilsons.domain.map.Coordinates;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A DVD. DVDs are a type of {@link Media} stored in a {@link ca.umanitoba.cs.egilsons.domain.Library}.
 */
public class DVD implements Media {
    private final String title;
    private final String director;
    private final int runTime; // in minutes
    private final MediaCategory category;
    private List<Review> reviews;
    private int copies;
    private Queue<Member> waitlist;
    private Coordinates coordinates;

    /**
     * Invariant properties for DVD.
     */
    private void checkDVD() {
        Preconditions.checkNotNull(title, "Title should never be null.");
        Preconditions.checkState(title.length() >= 1,"Book title should have at least 1 symbol.");
        Preconditions.checkNotNull(director, "Director should never be null.");
        Preconditions.checkState(director.length() >= 1,"Director name should have at least 1 symbol.");
        Preconditions.checkState(runTime > 0, "Run time should be above 0.");
        Preconditions.checkNotNull(category, "Category should never be null.");
        Preconditions.checkNotNull(reviews, "Reviews should never be null.");
        Preconditions.checkState(copies >= 0, "Copies should never go below 0.");
        Preconditions.checkNotNull(waitlist, "Waitlist should never be null.");
        Preconditions.checkNotNull(coordinates, "Coordinates should never be null.");

        for (Review review : reviews) {
            Preconditions.checkNotNull(review, "Reviews in reviews should never be null.");
        }

        for (Member member : waitlist) {
            Preconditions.checkNotNull(member, "Members in waitlist should never be null.");
        }
    }


    /**
     * Constructor for DVDs. Takes input for the title, director, run time and category.
     *
     * @param title the title of the DVD
     * @param director the director of the DVD
     * @param runTime the run time of the DVD
     * @param category the category or genre of the DVD
     */
    private DVD(String title, String director, int runTime, MediaCategory category) {
        this.title = title;
        this.director = director;
        this.runTime = runTime;
        this.category = category;
        this.reviews = new ArrayList<>();
        this.copies++;
        this.waitlist = new LinkedList<>();
        this.coordinates = Map.setMediaCoordinates(this);
        checkDVD();
    }

    /**
     * A builder class for a dvd
     */
    public static class DVDBuilder {
        private String title;
        private String director;
        private int runTime; // in minutes
        private MediaCategory category;

        public DVDBuilder title(String title) throws InvalidTitleException {
            Preconditions.checkNotNull(title, "Title should not be null.");

            if (title.isEmpty()) {
                throw new InvalidTitleException();
            }

            this.title = title;
            return this;
        }

        public DVDBuilder director(String director) throws InvalidDirectorException {
            Preconditions.checkNotNull(director, "Director should not be null.");

            if (director.isEmpty()) {
                throw new InvalidDirectorException();
            }

            this.director = director;
            return this;
        }

        public DVDBuilder runTime(int runTime) throws InvalidDVDRunTimeException {
            if (runTime < 1) {
                throw new InvalidDVDRunTimeException();
            }

            this.runTime = runTime;
            return this;
        }

        public DVDBuilder category(MediaCategory category) {
            Preconditions.checkNotNull(category, "Category should not be null.");

            this.category = category;
            return this;
        }

        public DVD build() {
            return new DVD(this.title, this.director, this.runTime, this.category);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public String getDirector() {
        return this.director;
    }

    public int getRunTime() {
        return this.runTime;
    }

    public MediaCategory getCategory() {
        return this.category;
    }

    public List<Review> getReviews() {
        return this.reviews;
    }

    public int getCopies() {
        return this.copies;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public Queue<Member> getWaitlist() {
        return this.waitlist;
    }

    /**
     * Checks if a member is at the front of the waitlist
     *
     * @param member the member being checked
     * @return if the member is at the front or not
     */
    public boolean frontOfWaitlist(Member member) {
        boolean isFront = false;
        if (this.waitlist.isEmpty() || this.waitlist.element().compareTo(member) == 0) {
            isFront = true;
        }
        return isFront;
    }

    /**
     * Checks if another media is equal to this DVD
     *
     * @param other the media being compared
     * @return if the two media are equal or not
     */
    public boolean equals(Media other) {
        checkDVD();
        boolean equal = false;
        // Checking the media is a DVD
        if (other instanceof DVD otherDVD) {
            // Only equal if the title, director, run time and category are all the same
            if (otherDVD.title.equals(this.title) && otherDVD.director.equals(this.director) && otherDVD.runTime == this.runTime
                    && otherDVD.category == this.category) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * Adds a copy of this DVD
     */
    public void addCopy() {
        checkDVD();
        this.copies++;
        checkDVD();
    }

    /**
     * Removes a copy of this media if a copy is available
     *
     * @return a copy of the media
     */
    public Media takeOutCopy() {
        checkDVD();
        Media copy = null;
        if (this.copies > 0) {
            this.copies--;
            copy = this;
        }
        checkDVD();
        return copy;
    }

    /**
     * Adds a member to the waitlist
     *
     * @param member the member to be added
     * @return the spot in the waitlist
     */
    public int addToWaitlist(Member member) {
        checkDVD();
        this.waitlist.add(member);
        checkDVD();
        return this.waitlist.size();
    }

    /**
     * Adds a review to the DVD
     *
     * @param review the review being added
     */
    public void addReview(Review review) {
        checkDVD();
        this.reviews.add(review);
        checkDVD();
    }
}
