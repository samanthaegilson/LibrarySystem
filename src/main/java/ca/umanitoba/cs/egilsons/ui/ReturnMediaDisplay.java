package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.logic.ReturnMedia;
import ca.umanitoba.cs.egilsons.output.ReviewPrinter;

import java.util.Scanner;

public class ReturnMediaDisplay {
    private final ReturnMedia returnMedia;
    private final Library library;
    private final Scanner keyboard;
    private final Member member;

    public ReturnMediaDisplay(Library library, Member member) {
        this.library = library;
        this.member = member;
        this.returnMedia = new ReturnMedia(library, member);
        this.keyboard = new Scanner(System.in);
    }

    public void startReturnMedia() {
        Media returning = null;

        while (returning == null) {
            System.out.println("Current media:");
            for (Media media : member.getTakenOut()) {
                System.out.println(media.getTitle() + ", " + media.getClass().getSimpleName());
            }

            System.out.println("Please enter the title of the media to return: ");
            String title = this.keyboard.nextLine();

            // In case two formats have the same name
            boolean isBook = formatChoice();
            for (Media media : member.getTakenOut()) {
                if (media.getTitle().equalsIgnoreCase(title)) {
                    if (isBook) {
                        if (media instanceof Book) {
                            returning = media;
                        }
                    } else {
                        if (media instanceof DVD) {
                            returning = media;
                        }
                    }
                }
            }

            if (returning == null) {
                System.out.println("No such media exists. Please try again.");
            }
        }

        // Choice of return or write review
    }

    private boolean formatChoice() {
        boolean isBook = false;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                Please enter the format of the media:
                BOOK
                DVD
                """);
            String format = this.keyboard.nextLine().toUpperCase();
            switch (format) {
                case "BOOK":
                    validInput = true;
                    isBook = true;
                    break;
                case "DVD":
                    validInput = true;
                    break;
                default:
                    System.out.println("Not an option.");
            }
        }
        return isBook;
    }

    private void returnMedia(Media media) {
        this.returnMedia.returnMedia(media);
        System.out.println(media.getTitle() + " has been returned to " + this.library.getName() + " Library.");
    }

    private void writeReview(Media media) {
        // need text and stars from user

    }

    private void readReview(Media media) {
        // cycle through the media's reviews and choose 1 to show
        // numbered list?
        System.out.println("Current reviews:");
        for (int i = 0; i < media.getReviews().size(); i++) {
            System.out.println(i + ". " + media.getReviews().get(i).member() + ", " + media.getReviews().get(i).stars()
                    + " stars");
        }
        System.out.println("Please select a review to view");
        int choice = getChoice(0, media.getReviews().size() - 1);

        ReviewPrinter reviewPrinter = new ReviewPrinter(media.getReviews().get(choice));
    }

    private int getChoice(int low, int high) {
        boolean valid = false;
        int choice = -1;
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                choice = Integer.parseInt(input);
                // The choice is only valid if it is an integer between the high and low bounds
                if (choice >= low && choice <= high) {
                    valid = true;
                } else {
                    System.out.println("Not a valid choice, please try again.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number, please try again.");
            }
        }
        return choice;
    }
}
