package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.media.DVD;

/**
 * Prints the information of a {@link DVD}.
 */
public class DVDPrinter implements Printer {
    private DVD dvd;

    public DVDPrinter(final DVD dvd) {
        this.dvd = dvd;
    }

    public void print() {
        System.out.println("DVD title: " + this.dvd.getTitle());
        System.out.println("Director: " + this.dvd.getDirector());
        System.out.println("Run time: " + this.dvd.getRunTime());
        System.out.println("Category: " + this.dvd.getCategory());
        System.out.println("Copies available: " + this.dvd.getCopies());

        System.out.println("Reviews: ");
        for (Review review : this.dvd.getReviews()) {
            System.out.println(review.getStars() + " stars by " + review.getMember().getName());
        }

        System.out.print("Waitlist: ");
        int counter = 1;
        for (Member member : this.dvd.getWaitlist()) {
            System.out.print(member.getName());
            if (counter < this.dvd.getWaitlist().size()) {
                System.out.print(", ");
            }
            counter++;
        }
        System.out.println();
    }
}
