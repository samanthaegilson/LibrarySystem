package ca.umanitoba.cs.egilsons;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.exceptions.*;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.domain.resource.Computer;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
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
            display.signInScreen();

            running = exitChoice();
        }
    }

    private static void setUpLibrarySystem() {
        // Library System and library
        librarySystem = new LibrarySystem();
        Library library1 = new Library("Shakespeare");

        // Media
        Book book1 = new Book("Harry Potter", "J.K. Rowling", 560, MediaCategory.FANTASY);
        Book book2 = new Book("The Hunger Games", "Suzanne Collins", 384, MediaCategory.YOUNG_ADULT);
        Book book3 = new Book("The Missing Page", "HTTP", 404, MediaCategory.MYSTERY);
        Book book4 = new Book("Gardening 101", "Mikayla Goncalves", 403, MediaCategory.NON_FICTION);
        Book book5 = new Book("Hollow Echoes", "Kristine Fisher", 963, MediaCategory.HORROR);
        Book book6 = new Book("Alien Invasion", "Oliver White", 668, MediaCategory.SCIENCE_FICTION);


        DVD dvd1 = new DVD("How to Train Your Cat", "Cat Sanders", 98, MediaCategory.CHILDREN);
        DVD dvd2 = new DVD("Fading Colours", "Julia Winters", 82, MediaCategory.ROMANCE);
        DVD dvd3 = new DVD("The Wishing Well", "Peter Callow", 103, MediaCategory.FANTASY);
        DVD dvd4 = new DVD("The Red Road", "Steven Anderson", 114, MediaCategory.HISTORICAL_FICTION);
        DVD dvd5 = new DVD("Starship", "Jamie Jones", 200, MediaCategory.SCIENCE_FICTION);

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
                    System.out.println("Must be a number between 1 and " + high + ", e.g., 1.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number, please enter a number between 1 and " + high + ", e.g., 1.");
            }
        }
        return choice;
    }
}
