package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.Review;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReviewStarsException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidReviewTextException;
import ca.umanitoba.cs.egilsons.domain.media.Loan;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.logic.ReturnMedia;
import ca.umanitoba.cs.egilsons.output.ReviewPrinter;
import com.google.common.base.Preconditions;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Returns a {@link Media} to the {@link Library}.
 */
public class ReturnMediaDisplay {
    private final ReturnMedia returnMedia;
    private final Library library;
    private final Scanner keyboard;
    private final Member member;

    /**
     * A constructor for ReturnMediaDisplay. Receives the library and the member
     *
     * @param library the library to return the media to
     * @param member the member returning the media
     */
    public ReturnMediaDisplay(Library library, Member member) {
        this.library = library;
        this.member = member;
        this.returnMedia = new ReturnMedia(member);
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Runs the return options until the media is returned
     */
    public void startReturnMedia() {
        if (!member.getTakenOut().isEmpty()) {
            Media returning;
            printMediaOptions(member.getTakenOut());

            System.out.println("Please select the media to return: ");
            int mediaChoice = getChoice(member.getTakenOut().size()) - 1;
            returning = member.getTakenOut().get(mediaChoice).getMedia();

            // Choice of return or write or read review
            boolean doneReturn = false;
            while (!doneReturn) {
                int choice = returnOptions();
                if (choice == 1) {
                    doneReturn = true;
                    returnMedia(returning);
                } else if (choice == 2) {
                    readReview(returning);
                } else {
                    writeReview(returning);
                }
            }
        } else {
            System.out.println("You do not have any media taken out.");
        }
    }

    /**
     * Prints the media currently taken out
     *
     * @param loanList the media currently taken out
     */
    private void printMediaOptions(List<Loan> loanList) {
        int index = 1;
        System.out.println("Current media: ");
        for (Loan loan : loanList) {
            System.out.println(index + ". " + loan.getMedia().getTitle() + ", "
                    + loan.getMedia().getClass().getSimpleName());
            index++;
        }
    }

    /**
     * Gets the user's choice on return option
     *
     * @return 1 to return media, 2 to read reviews or 3 to write a review
     */
    private int returnOptions() {
        System.out.println("""
                Would you like to:
                1. RETURN MEDIA
                2. READ REVIEWS
                3. WRITE REVIEW""");
        return getChoice(3); // There are three options to choose from
    }

    /**
     * Returns the media to the library
     *
     * @param media the media to return
     */
    private void returnMedia(Media media) {
        this.returnMedia.returnMedia(media);
        System.out.println(media.getTitle() + " has been returned to " + this.library.getName() + " Library.");
    }

    /**
     * Writes a review about the media
     *
     * @param media the media to write a review about
     */
    private void writeReview(Media media) {
        Review.ReviewBuilder reviewBuilder = new Review.ReviewBuilder();
        // We already have the member making the review and the media the review is about
        reviewBuilder.member(this.member);
        reviewBuilder.media(media);
        // We still need to get the review text and stars from the user
        getTextInput(reviewBuilder);
        getStarsInput(reviewBuilder);

        returnMedia.writeReview(media, reviewBuilder.build());
        System.out.println("Review for " + media.getTitle() + " has been created.");
    }

    /**
     * Receives the text for the review from the user
     *
     * @param reviewBuilder the builder for the review
     */
    private void getTextInput(Review.ReviewBuilder reviewBuilder) {
        String text;
        Preconditions.checkNotNull(reviewBuilder, "Builder should not be null");
        do {
            System.out.println("Enter text for the review: ");
            text = this.keyboard.nextLine();

            try {
                reviewBuilder.text(text);
            } catch (InvalidReviewTextException e) {
                System.out.println("Review text should have at least one letter, e.g., Love it");
                text = null;
            }
        } while (text == null);
        Preconditions.checkNotNull(text, "Text should not be null after it's been set.");
    }

    /**
     * Receives the stars for the review from the user
     *
     * @param reviewBuilder the builder for the review
     */
    private void getStarsInput(Review.ReviewBuilder reviewBuilder) {
        int stars = -1;
        Preconditions.checkNotNull(reviewBuilder, "Builder should not be null");

        do {
            System.out.println("Please enter a star rating (1-5): ");
            try {
                stars = this.keyboard.nextInt();
                this.keyboard.nextLine(); // Remove extra newline after successful nextInt();
                reviewBuilder.stars(stars);
            } catch (InputMismatchException e) {
                System.out.println("Stars must be a positive whole number between 1 and 5, e.g., 3");
                stars = -1;
                this.keyboard.nextLine(); // Remove extra newline after nextInt();
            } catch (InvalidReviewStarsException e) {
                System.out.println("Stars must be a positive whole number between 1 and 5, e.g., 3");
                stars = -1;
            }
        } while (stars == -1);
        Preconditions.checkState(stars != -1, "Stars should not be negative if it's been set.");
    }

    /**
     * Prints a review about the media
     *
     * @param media the media of the reviews
     */
    private void readReview(Media media) {
        if (!media.getReviews().isEmpty()) {
            System.out.println("Current reviews:");
            for (int i = 0; i < media.getReviews().size(); i++) {
                System.out.println((i + 1) + ". " + media.getReviews().get(i).getMember().getName() + ", "
                        + media.getReviews().get(i).getStars() + " stars");
            }
            System.out.println("Please select a review to view");
            int reviewChoice = getChoice(media.getReviews().size()) - 1;

            ReviewPrinter reviewPrinter = new ReviewPrinter(media.getReviews().get(reviewChoice));
            reviewPrinter.print();
        } else {
            System.out.println("There are currently no reviews for " + media.getTitle());
        }
    }

    /**
     * Gets an integer from the user between 1 and a high bound.
     *
     * @param high the highest the choice can be
     * @return the integer representing the choice of the user
     */
    private int getChoice(int high) {
        boolean valid = false;
        int choice = -1;
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                choice = Integer.parseInt(input);
                // The choice is only valid if it is an integer between the high and low bounds
                if (choice >= 1 && choice <= high) {
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
