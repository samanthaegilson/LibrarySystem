package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Review;

/**
 * Prints the information of a {@link Review}.
 */
public class ReviewPrinter implements Printer {
    private Review review;

    public ReviewPrinter(final Review review) {
        this.review = review;
    }

    public void print() {
        System.out.println("Media: " + this.review.getMedia().getTitle());
        System.out.println("Author: " + this.review.getMember().getName());
        System.out.println(this.review.getStars() + " stars");
        System.out.println(this.review.getText());
    }
}
