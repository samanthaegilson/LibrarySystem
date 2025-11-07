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
            String choice = browseOptions();
            switch (choice) {
                case "choose media":
                    borrowed = chooseMedia();
                    break;
                case "filter choices":
                    filterChoices();
                    break;
                default:
                    System.out.println("Not an option.");
            }
        }
    }

    private String browseOptions() {
        String choice;
        System.out.println("""
                Would you like to:
                CHOOSE MEDIA
                FILTER CHOICES
                """);
        choice = this.keyboard.nextLine().toLowerCase();
        return choice;
    }

    private void printMediaOptions(List<Media> mediaList) {
        System.out.println("Media: ");
        for (Media media : mediaList) {
            System.out.println(media.getTitle() + ", " + media.getClass().getSimpleName());
        }
    }

    private boolean chooseMedia() {
        // moves on to borrowing
        // print full media info before choosing
        boolean borrowed = false;
        Media chosenMedia = null;
        while (chosenMedia == null) {
            System.out.println("Please enter the title of your chosen media: ");
            String title = this.keyboard.nextLine();

            System.out.println("Please enter the format of your chosen media: ");
            boolean isBook = formatChoice();
            if (isBook) {
                for (Media media : library.getMedia()) {
                    if (media.getTitle().equalsIgnoreCase(title) && media instanceof Book) {
                        chosenMedia = media;
                        BookPrinter bookPrinter = new BookPrinter((Book) chosenMedia);
                        bookPrinter.print();
                    }
                }
            } else {
                for (Media media : library.getMedia()) {
                    if (media.getTitle().equalsIgnoreCase(title) && media instanceof DVD) {
                        chosenMedia = media;
                        DVDPrinter dvdPrinter = new DVDPrinter((DVD) chosenMedia);
                        dvdPrinter.print();
                    }
                }
            }

            if (chosenMedia == null) {
                System.out.println("No such media. Please try again.");
            }
        }

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
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
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

    private boolean yesNo() {
        boolean isYes = false;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                YES
                NO
                """);
            String yesNo = this.keyboard.nextLine().toLowerCase();
            switch (yesNo) {
                case "yes":
                    validInput = true;
                    isYes = true;
                    break;
                case "no":
                    validInput = true;
                    break;
                default:
                    System.out.println("Not an option.");
            }
        }
        return isYes;
    }

    private void filterChoices() {
        // applies filter and prints new choices before going back
        // choices for filter by: name, type (is type category or book/dvd??)
        System.out.println("Please enter the name to filter by: ");
        String name = this.keyboard.nextLine().toLowerCase();

        System.out.println("Please enter the format to filter by: ");
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
        }
    }
}
