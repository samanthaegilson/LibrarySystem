package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.media.Review;

/**
 * Prints the information of a {@link Review}.
 */
public class ReviewPrinter implements Printer {
    private Review review;

    public ReviewPrinter(final Review review) {
        this.review = review;
    }

    public void print() {
        System.out.println("Media: " + this.review.media().getName());
        System.out.println("Author: " + this.review.member().getName());
        System.out.println(this.review.stars() + " stars");
        System.out.println(this.review.review());
    }
}
