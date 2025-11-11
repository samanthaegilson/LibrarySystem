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

public class BorrowMediaDisplay {
    private final BorrowMedia borrowMedia;
    private final Library library;
    private final Scanner keyboard;
    private final Member member;

    public BorrowMediaDisplay(Library library, Member member) {
        this.library = library;
        this.member = member;
        this.borrowMedia = new BorrowMedia(library, member);
        this.keyboard = new Scanner(System.in);
    }

    public void browseMedia() {
        // print all media then ask to filter
        // should pass the library and member
        printMediaOptions(this.library.getMedia());

        boolean borrowed = false;
        while (!borrowed) {
            boolean chooseMedia = browseOptions();
            if (chooseMedia) {
                borrowed = chooseMedia();
            } else {
                filterChoices();
            }
        }
    }

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

    private void printMediaOptions(List<Media> mediaList) {
        int index = 1;
        System.out.println("Media: ");
        for (Media media : mediaList) {
            System.out.println(index + ". " + media.getTitle() + ", " + media.getClass().getSimpleName());
            index++;
        }
    }

    private boolean chooseMedia() {
        // moves on to borrowing
        // print full media info before choosing
        boolean borrowed = false;
        Media chosenMedia;
        // Gets choice of media
        System.out.println("Please select a media: ");
        int mediaChoice = getChoice(this.library.getMedia().size()) - 1;
        chosenMedia = this.library.getMedia().get(mediaChoice);

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
                borrowed = true;
            } else {
                waitlist(chosenMedia);
            }
        }

        return borrowed;
    }

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

    private void filterChoices() {
        // applies filter and prints new choices before going back
        // choices for filter by: name, type (is type category or book/dvd??)
        System.out.println("Please enter the name to filter by: ");
        String name = this.keyboard.nextLine().toLowerCase();

        System.out.println("Please choose the format to filter by: ");
        boolean isBook = formatChoice();

        List<Media> filtered = this.borrowMedia.filterMedia(name, isBook);
        printMediaOptions(filtered);
    }

    private void waitlist(Media media) {
        System.out.println(media.getTitle() + " has no available copies.\nWould you like to be added to the waitlist?");
        boolean joinWaitlist = yesNo();
        if (joinWaitlist) {
            int spot = media.addToWaitlist(this.member);
            System.out.println(this.member.getName() + " is at spot " + spot + " in the waitlist for " + media.getTitle());
            System.out.println("You will be notified when " + media.getTitle() + " is available.");
            // NEED TO NOTIFY MEMBER WHEN THEY SIGN IN
            // Make a new instance variable in Member called announcements
            // Print all announcements when a user signs in
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
