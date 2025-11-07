package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;

import java.util.Scanner;

public class AccountCentre {
    // Choose here to browse media, book a resource, return media, find a path to an item, or log out
    private final Library library;
    private final Scanner keyboard;
    private final Member member;

    public AccountCentre(Library library, Member member) {
        this.library = library;
        this.member = member;
        this.keyboard = new Scanner(System.in);
    }

    public void menu() {
        boolean running = true;
        String choice;
        System.out.println("Welcome " + this.member.getName() + "!");
        while (running) {
            choice = accountChoices();
            switch (choice) {
                case "borrow media":
                    borrowMedia();
                    break;
                case "return media":
                    returnMedia();
                    break;
                case "book resource":
                    bookResource();
                    break;
                case "find item":
                    findItem();
                    break;
                case "log out":
                    running = false;
//                    this.keyboard.close();
                    break;
                default:
                    System.out.println("Not an option.");
            }
        }
    }

    private String accountChoices() {
        String choice;
        System.out.println("""
                What would you like to do:
                BORROW MEDIA
                RETURN MEDIA
                BOOK RESOURCE
                FIND ITEM
                LOG OUT
                """);
        choice = keyboard.nextLine().toLowerCase();
        return choice;
    }

    private void borrowMedia() {
        BorrowMediaDisplay borrowMediaDisplay = new BorrowMediaDisplay(this.library, this.member);
        borrowMediaDisplay.browseMedia();
    }

    private void returnMedia() {

    }

    private void bookResource() {

    }

    private void findItem() {

    }
}
