package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.media.Loan;
import ca.umanitoba.cs.egilsons.logic.BookResource;
import ca.umanitoba.cs.egilsons.logic.BorrowMedia;
import ca.umanitoba.cs.egilsons.logic.FindItem;
import ca.umanitoba.cs.egilsons.logic.ReturnMedia;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;

import java.util.Scanner;

/**
 * UI for the account centre. Where the user decides what task to do
 */
public class AccountCentre {
    private final LibrarySystem librarySystem;
    private final Library library;
    private final Scanner keyboard;
    private final Member member;
    private final LibrarySystemPersistence persistence;

    /**
     * A constructor for AccountCentre. Receives the library and member
     *
     * @param librarySystem the library system
     * @param member the member of the account
     * @param persistence the persistence of the library system
     */
    public AccountCentre(LibrarySystem librarySystem, Member member, LibrarySystemPersistence persistence) {
        this.librarySystem = librarySystem;
        this.library = librarySystem.getLibraries().get(0);
        this.member = member;
        this.persistence = persistence;
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Runs the menu until the user logs out
     */
    public void menu() {
        boolean running = true;
        int choice;
        System.out.println("Welcome " + this.member.getName() + "!");
        while (running) {
            printAnnouncements();
            choice = accountChoices();
            if (choice == 1) {
                borrowMedia();
            } else if (choice == 2) {
                returnMedia();
            } else if (choice == 3) {
                bookResource();
            } else if (choice == 4) {
                findItem();
            } else {
                running = false;
            }
        }
    }

    /**
     * Prints any waitlist announcements
     */
    private void printAnnouncements() {
        System.out.println("Announcements: ");
        for (String announcement : this.member.getAnnouncements()) {
            System.out.println(announcement + " is available to take out!");
        }
        checkForOverdue();
    }

    /**
     * Prints out if any media is overdue
     */
    private void checkForOverdue() {
        for (Loan loan : this.member.getTakenOut()) {
            if (loan.isOverdue()) {
                System.out.println(loan.getMedia().getTitle() + " is overdue. You will not be able to take out any "
                        + "new media until " + loan.getMedia().getTitle() + " is returned.");
            }
        }
    }

    /**
     * Gets the user's choice of task
     *
     * @return the choice of task
     */
    private int accountChoices() {
        System.out.println("""
                What would you like to do:
                1. BORROW MEDIA
                2. RETURN MEDIA
                3. BOOK RESOURCE
                4. FIND ITEM
                5. LOG OUT""");
        return getChoice(5); // There are five possible choices
    }

    /**
     * Borrows a piece of media from the library
     */
    private void borrowMedia() {
        if (!this.member.hasOverdueMedia()) {
            BorrowMedia borrowMedia = new BorrowMedia(this.librarySystem, this.member, this.persistence);
            BorrowMediaDisplay borrowMediaDisplay = new BorrowMediaDisplay(borrowMedia, this.library, this.member);
            borrowMediaDisplay.browseMedia();
        } else {
            System.out.println("You cannot borrow media until all overdue media is returned.");
        }
    }

    /**
     * Returns a media that was taken out
     */
    private void returnMedia() {
        if (!this.member.getTakenOut().isEmpty()) {
            ReturnMedia returnMedia = new ReturnMedia(this.librarySystem, this.member, this.persistence);
            ReturnMediaDisplay returnMediaDisplay = new ReturnMediaDisplay(returnMedia, this.library, this.member);
            returnMediaDisplay.startReturnMedia();
        } else {
            System.out.println("You have no media to return.");
        }
    }

    /**
     * Books a time slot of a resource
     */
    private void bookResource() {
        BookResource bookResource = new BookResource(this.librarySystem, this.member, this.persistence);
        BookResourceDisplay bookResourceDisplay = new BookResourceDisplay(bookResource, this.library);
        bookResourceDisplay.bookResourceSlot();
    }

    /**
     * Finds a path from the library kiosk to an item in the library
     */
    private void findItem() {
        FindItem findItem = new FindItem(this.library);
        FindItemDisplay findItemDisplay = new FindItemDisplay(findItem, this.library);
        findItemDisplay.enterItem();
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
