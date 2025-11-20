package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidAuthorException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidBookPagesException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidTitleException;
import ca.umanitoba.cs.egilsons.domain.map.Coordinates;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A book. Books are a type of {@link Media} stored in a {@link ca.umanitoba.cs.egilsons.domain.Library}.
 */
public class Book implements Media {
    private final String title;
    private final String author;
    private final int pages;
    private final MediaCategory category;
    private List<Review> reviews;
    private int copies;
    private Queue<Member> waitlist;
    private final Coordinates coordinates;

    /**
     * Invariant properties for Book.
     */
    private void checkBook() {
        Preconditions.checkNotNull(title, "Title should never be null.");
        Preconditions.checkState(title.length() >= 1,"Book title should have at least 1 symbol.");
        Preconditions.checkNotNull(author, "Author should never be null.");
        Preconditions.checkState(author.length() >= 1,"Author name should have at least 1 symbol.");
        Preconditions.checkState(pages > 0, "Pages should be above 0.");
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
     * Constructor for books. Takes input for the title, author, pages and category.
     *
     * @param title the title of the book
     * @param author the author of the book
     * @param pages the amount of pages in the book
     * @param category the category or genre of the book
     */
    private Book(String title, String author, int pages, MediaCategory category) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.category = category;
        this.reviews = new ArrayList<>();
        this.copies++;
        this.waitlist = new LinkedList<>();
        this.coordinates = Map.setMediaCoordinates(this);
        checkBook();
    }

    /**
     * A builder class for a book
     */
    public static class BookBuilder {
        private String title;
        private String author;
        private int pages;
        private MediaCategory category;

        public BookBuilder title(String title) throws InvalidTitleException {
            Preconditions.checkNotNull(title, "Title should not be null.");

            if (title.isEmpty()) {
                throw new InvalidTitleException();
            }

            this.title = title;
            return this;
        }

        public BookBuilder author(String author) throws InvalidAuthorException {
            Preconditions.checkNotNull(author, "Author should not be null.");

            if (author.isEmpty()) {
                throw new InvalidAuthorException();
            }

            this.author = author;
            return this;
        }

        public BookBuilder pages(int pages) throws InvalidBookPagesException {
            if (pages < 1) {
                throw new InvalidBookPagesException();
            }

            this.pages = pages;
            return this;
        }

        public BookBuilder category(MediaCategory category) {
            Preconditions.checkNotNull(category, "Category should not be null.");

            this.category = category;
            return this;
        }

        public Book build() {
            return new Book(this.title, this.author, this.pages, this.category);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getPages() {
        return this.pages;
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
     * Checks if another media is equal to this book
     *
     * @param other the media being compared
     * @return if the two media are equal or not
     */
    public boolean equals(Media other) {
        checkBook();
        boolean equal = false;
        // Checking the media is a book
        if (other instanceof Book otherBook) {
            // Only equal if the title, author, page number and category are all the same
            if (otherBook.title.equals(this.title) && otherBook.author.equals(this.author) && otherBook.pages == this.pages
                    && otherBook.category == this.category) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * Adds a copy of this book
     */
    public void addCopy() {
        checkBook();
        this.copies++;
        checkBook();
    }

    /**
     * Removes a copy of this media if a copy is available
     *
     * @return a copy of the media
     */
    public Media takeOutCopy() {
        checkBook();
        Media copy = null;
        if (this.copies > 0) {
            this.copies--;
            copy = this;
        }
        checkBook();
        return copy;
    }

    /**
     * Adds a member to the waitlist
     *
     * @param member the member to be added
     * @return the spot in the waitlist
     */
    public int addToWaitlist(Member member) {
        checkBook();
        this.waitlist.add(member);
        checkBook();
        return this.waitlist.size();
    }

    /**
     * Adds a review to the book
     *
     * @param review the review being added
     */
    public void addReview(Review review) {
        checkBook();
        this.reviews.add(review);
        checkBook();
    }
}
