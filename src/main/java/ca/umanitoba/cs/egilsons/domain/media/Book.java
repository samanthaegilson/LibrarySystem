package ca.umanitoba.cs.egilsons.domain.media;

import ca.umanitoba.cs.egilsons.domain.Review;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

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

        for (Review review : reviews) {
            Preconditions.checkNotNull(review, "Reviews in reviews should never be null.");
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
    public Book(String title, String author, int pages, MediaCategory category) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.category = category;
        this.reviews = new ArrayList<>();
        this.copies++;
        checkBook();
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
