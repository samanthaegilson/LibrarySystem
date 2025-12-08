package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.logic.BorrowMedia;
import ca.umanitoba.cs.egilsons.output.BookPrinter;
import ca.umanitoba.cs.egilsons.output.DVDPrinter;

import java.util.List;
import java.util.Scanner;

/**
 * Borrows a {@link Media} from the {@link Library}.
 */
public class BorrowMediaDisplay {
    private final BorrowMedia borrowMedia;
    private final Library library;
    private final Scanner keyboard;
    private final Member member;

    /**
     * A constructor for BorrowMediaDisplay. Receives the library and the member
     *
     * @param borrowMedia the media borrowing to display
     * @param library the library of the media
     * @param member the member borrowing media
     */
    public BorrowMediaDisplay(BorrowMedia borrowMedia, Library library, Member member) {
        this.borrowMedia = borrowMedia;
        this.library = library;
        this.member = member;
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Prints media options until a media is borrowed
     */
    public void browseMedia() {
        printMediaOptions(this.library.getMedia());
        List<Media> options = this.library.getMedia();

        boolean borrowed = false;
        while (!borrowed) {
            boolean chooseMedia = browseOptions();
            if (chooseMedia) {
                borrowed = chooseMedia(options);
            } else {
                options = filterChoices();
            }
        }
    }

    /**
     * Gets the user's choice of to choose a media or filter
     *
     * @return if the user will choose a media or not
     */
    private boolean browseOptions() {
        boolean chooseMedia = false;
        System.out.println("""
                Would you like to:
                1. CHOOSE MEDIA
                2. FILTER CHOICES""");
        int choice = getChoice(2); // There are only two options
        if (choice == 1) {
            chooseMedia = true;
        }
        return chooseMedia;
    }

    /**
     * Prints the media choices
     *
     * @param mediaList the media to choose from
     */
    private void printMediaOptions(List<Media> mediaList) {
        int index = 1;
        System.out.println("Media: ");
        for (Media media : mediaList) {
            System.out.println(index + ". " + media.getTitle() + ", " + media.getClass().getSimpleName());
            index++;
        }
    }

    /**
     * Chooses the media to borrow and borrows it if available
     *
     * @param options the list of media to choose from
     * @return if the media was borrowed or not
     */
    private boolean chooseMedia(List<Media> options) {
        // moves on to borrowing
        // print full media info before choosing
        boolean borrowed = false;
        Media chosenMedia;
        // Gets choice of media
        System.out.println("Please select a media: ");
        int mediaChoice = getChoice(options.size()) - 1;
        chosenMedia = options.get(mediaChoice);

        // There are only two types of media
        if (chosenMedia instanceof Book) {
            BookPrinter bookPrinter = new BookPrinter((Book) chosenMedia);
            bookPrinter.print();
        } else {
            DVDPrinter dvdPrinter = new DVDPrinter((DVD) chosenMedia);
            dvdPrinter.print();
        }

        // Prints full media information before deciding to borrow
        System.out.println("Would you like to borrow this media:");
        boolean borrow = yesNo();
        if (borrow) {
            if (borrowMedia.borrowMedia(chosenMedia)) {
                System.out.println(chosenMedia.getTitle() + " has been taken out.");
            } else {
                waitlist(chosenMedia);
            }
            borrowed = true;
        }

        return borrowed;
    }

    /**
     * Gets the user's choice of a book or dvd
     *
     * @return if the user chose a book or not
     */
    private boolean formatChoice() {
        boolean isBook = false;
        System.out.println("""
            1. BOOK
            2. DVD""");
        int format = getChoice(2); // There are only two format options
        if (format == 1) {
            isBook = true;
        }
        return isBook;
    }

    /**
     * Gets the user's choice of yes or no
     *
     * @return if the user chose yes or not
     */
    private boolean yesNo() {
        boolean isYes = false;
        System.out.println("""
            1. YES
            2. NO""");
        int yesNo = getChoice(2);
        if (yesNo == 1) {
            isYes = true;
        }
        return isYes;
    }

    /**
     * Filters media by name and format
     *
     * @return the filtered list of media
     */
    private List<Media> filterChoices() {
        System.out.println("Please enter the name to filter by: ");
        String name = this.keyboard.nextLine().toLowerCase();

        System.out.println("Please choose the format to filter by: ");
        boolean isBook = formatChoice();

        List<Media> filtered = this.borrowMedia.filterMedia(name, isBook);
        printMediaOptions(filtered);
        return filtered;
    }

    /**
     * Adds member to waitlist if wanted
     *
     * @param media the media of the waitlist
     */
    private void waitlist(Media media) {
        System.out.println(media.getTitle() + " has no available copies.\nWould you like to be added to the waitlist?");
        boolean joinWaitlist = yesNo();
        if (joinWaitlist) {
            int spot = this.borrowMedia.addToWaitlist(media);
            if (spot > 0) {
                System.out.println(this.member.getName() + " is at spot " + spot + " in the waitlist for " + media.getTitle());
                System.out.println("You will be notified when " + media.getTitle() + " is available.");
            } else {
                System.out.println("You are already in the waitlist.");
            }
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
                    System.out.println("Must be a number between 1 and " + high + ", e.g., 1.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number, please enter a number between 1 and " + high + ", e.g., 1.");
            }
        }
        return choice;
    }
}
