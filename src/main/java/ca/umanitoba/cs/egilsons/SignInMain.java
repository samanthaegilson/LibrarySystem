package ca.umanitoba.cs.egilsons;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.exceptions.*;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.domain.resource.Computer;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import ca.umanitoba.cs.egilsons.ui.AccountCentre;
import ca.umanitoba.cs.egilsons.ui.SignInDisplay;

import java.util.Scanner;

public class SignInMain {
    private static LibrarySystem librarySystem;

    public static void main(String[] args) {
        setUpLibrarySystem();
        Library library = librarySystem.getLibraries().get(0);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to " + library.getName() + " Library");

            // our entry point is really only responsible for initializing our display class.
            SignInDisplay display = new SignInDisplay(librarySystem);
            Member account = display.startSignIn();

            // Add AccountCentre code to main instead?
            AccountCentre accountCentre = new AccountCentre(library, account);
            accountCentre.menu();

            running = exitChoice();
        }
    }

    private static void setUpLibrarySystem() {
        // Library System and library
        librarySystem = new LibrarySystem();
        Library library1 = new Library("Shakespeare");

        // Media
        try {
            Book book1 = new Book.BookBuilder().title("Harry Potter").author("J.K. Rowling").pages(560).category(MediaCategory.FANTASY).build();
            Book book2 = new Book.BookBuilder().title("The Hunger Games").author("Suzanne Collins").pages(384).category(MediaCategory.YOUNG_ADULT).build();
            Book book3 = new Book.BookBuilder().title("The Missing Page").author("HTTP").pages(404).category(MediaCategory.MYSTERY).build();
            Book book4 = new Book.BookBuilder().title("Gardening 101").author("Mikayla Goncalves").pages(403).category(MediaCategory.NON_FICTION).build();
            Book book5 = new Book.BookBuilder().title("Hollow Echoes").author("Kristine Fisher").pages(963).category(MediaCategory.HORROR).build();
            Book book6 = new Book.BookBuilder().title("Alien Invasion").author("Oliver White").pages(668).category(MediaCategory.SCIENCE_FICTION).build();


            DVD dvd1 = new DVD.DVDBuilder().title("How to Train Your Cat").director("Cat Sanders").runTime(98).category(MediaCategory.CHILDREN).build();
            DVD dvd2 = new DVD.DVDBuilder().title("Fading Colours").director("Julia Winters").runTime(82).category(MediaCategory.ROMANCE).build();
            DVD dvd3 = new DVD.DVDBuilder().title("The Wishing Well").director("Peter Callow").runTime(103).category(MediaCategory.FANTASY).build();
            DVD dvd4 = new DVD.DVDBuilder().title("The Red Road").director("Steven Anderson").runTime(114).category(MediaCategory.HISTORICAL_FICTION).build();
            DVD dvd5 = new DVD.DVDBuilder().title("Starship").director("Jamie Jones").runTime(200).category(MediaCategory.SCIENCE_FICTION).build();

            library1.addMedia(book1);
            for (int i = 0; i < 6; i++) {
                library1.addMedia(book2);
                library1.addMedia(book3);
                library1.addMedia(dvd4);
                library1.addMedia(book5);
                library1.addMedia(book6);
                library1.addMedia(dvd1);
                library1.addMedia(dvd2);
                library1.addMedia(dvd2);
                library1.addMedia(dvd5);
                library1.addMedia(dvd5);
                library1.addMedia(dvd5);
            }
            for (int i = 0; i < 5; i++) {
                library1.addMedia(book4);
                library1.addMedia(book2);
                library1.addMedia(book5);
                library1.addMedia(book2);
                library1.addMedia(book6);
                library1.addMedia(dvd1);
                library1.addMedia(dvd3);
                library1.addMedia(dvd3);
                library1.addMedia(book6);
            }
        } catch (InvalidTitleException | InvalidAuthorException | InvalidBookPagesException | InvalidDirectorException |
                 InvalidDVDRunTimeException e) {
            throw new RuntimeException(e);
        }

        // Resources
        for (int i = 0; i < 3; i++) {
            library1.addResource(new Room());
        }


        for (int i = 0; i < 9; i++) {
            library1.addResource(new Computer());
        }

        librarySystem.addLibrary(library1);
    }

    private static boolean exitChoice() {
        boolean running = true;
        System.out.println("""
            Would you like to exit?
            1. YES
            2. NO""");
        int choice = getChoice(2); // There are only two options
        if (choice == 1) {
            running = false;
        }
        return running;
    }

    /**
     * Gets an integer from the user between 1 and a high bound.
     *
     * @param high the highest the choice can be
     * @return the integer representing the choice of the user
     */
    public static int getChoice(int high) {
        Scanner keyboard = new Scanner(System.in);
        boolean valid = false;
        int choice = -1;
        while (!valid) {
            String input = keyboard.nextLine();
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
