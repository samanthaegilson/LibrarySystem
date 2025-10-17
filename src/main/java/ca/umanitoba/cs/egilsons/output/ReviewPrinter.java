package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Review;

public class ReviewPrinter implements Printer {
    private Review review;

    public ReviewPrinter(final Review review) {
        this.review = review;
    }

    public void print() {
        System.out.println("Media: " + this.review.getMedia());
        System.out.println("Author: " + this.review.getMember());
        System.out.println(this.review.getStars() + " stars");
        System.out.println(this.review.getReview());
    }
}
