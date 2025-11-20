package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.media.Book;

/**
 * Prints the information of a {@link Book}.
 */
public class BookPrinter implements Printer {
    private Book book;

    public BookPrinter(final Book book) {
        this.book = book;
    }

    public void print() {
        System.out.println("Book title: " + this.book.getTitle());
        System.out.println("Author: " + this.book.getAuthor());
        System.out.println("Pages: " + this.book.getPages());
        System.out.println("Category: " + this.book.getCategory());
        System.out.println("Copies available: " + this.book.getCopies());

        System.out.println("Reviews: ");
        for (Review review : this.book.getReviews()) {
            System.out.println(review.getStars() + " stars by " + review.getMember().getName());
        }

        System.out.print("Waitlist: ");
        int counter = 1;
        for (Member member : this.book.getWaitlist()) {
            System.out.print(member.getName());
            if (counter < this.book.getWaitlist().size()) {
                System.out.print(", ");
            }
            counter++;
        }
        System.out.println();
    }
}
