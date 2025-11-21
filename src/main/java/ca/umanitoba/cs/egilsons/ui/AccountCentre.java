package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.media.Loan;

import java.util.Scanner;

/**
 * UI for the account centre. Where the user decides what task to do
 */
public class AccountCentre {
    private final Library library;
    private final Scanner keyboard;
    private final Member member;

    /**
     * A constructor for AccountCentre. Receives the library and member
     *
     * @param library the library
     * @param member the member of the account
     */
    public AccountCentre(Library library, Member member) {
        this.library = library;
        this.member = member;
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
            BorrowMediaDisplay borrowMediaDisplay = new BorrowMediaDisplay(this.library, this.member);
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
            ReturnMediaDisplay returnMediaDisplay = new ReturnMediaDisplay(this.library, this.member);
            returnMediaDisplay.startReturnMedia();
        } else {
            System.out.println("You have no media to return.");
        }
    }

    /**
     * Books a time slot of a resource
     */
    private void bookResource() {
        BookResourceDisplay bookResourceDisplay = new BookResourceDisplay(this.library, this.member);
        bookResourceDisplay.bookResourceSlot();
    }

    /**
     * Finds a path from the library kiosk to an item in the library
     */
    private void findItem() {
        FindItemDisplay findItemDisplay = new FindItemDisplay(this.library);
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
