package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import ca.umanitoba.cs.egilsons.domain.map.MapType;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.media.DVD;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.logic.FindItem;
import ca.umanitoba.cs.egilsons.output.BookPrinter;
import ca.umanitoba.cs.egilsons.output.DVDPrinter;
import ca.umanitoba.cs.egilsons.output.MapPrinter;

import java.util.Scanner;

public class FindItemDisplay {
    private final FindItem findItem;
    private final Library library;
    private final Scanner keyboard;

    public FindItemDisplay(Library library) {
        this.findItem = new FindItem(library.getMap());
        this.library = library;
        this.keyboard = new Scanner(System.in);
    }

    public void enterItem() {
        // Will take out later
//        MapPrinter mapPrinter = new MapPrinter(this.library.getMap());
//        mapPrinter.print();
        /////////////
        MapType[][] path;

        boolean isMedia = findItemType();
        if (isMedia) {
            Media media = chooseMedia();
            path = findItem.findItemPath(media.getCoordinates());
        } else {
            Resource resource = chooseResource();
            path = findItem.findItemPath(resource.getCoordinates());
        }

        MapPrinter mapPrinter = new MapPrinter(new Map(path));
        mapPrinter.print();
    }

    private boolean findItemType() {
        boolean isMedia = false;
        System.out.println("""
            Please choose what kind of item you are looking for:
            1. MEDIA
            2. RESOURCE""");
        int choice = getChoice(2); // There are only two options
        if (choice == 1) {
            isMedia = true;
        }
        return isMedia;
    }

    private Media chooseMedia() {
        Media chosenMedia;
        printMediaOptions();
        // Gets choice of media
        System.out.println("Please select the media you are looking for: ");
        int mediaChoice = getChoice(this.library.getMedia().size()) - 1;
        chosenMedia = this.library.getMedia().get(mediaChoice);

        // DO I NEED TO PRINT THE FULL INFO????
        // There are only two types of media
        if (chosenMedia instanceof Book) {
            BookPrinter bookPrinter = new BookPrinter((Book) chosenMedia);
            bookPrinter.print();
        } else {
            DVDPrinter dvdPrinter = new DVDPrinter((DVD) chosenMedia);
            dvdPrinter.print();
        }

        return chosenMedia;
    }

    private Resource chooseResource() {
        printResourceOptions();
        int choice = getChoice(this.library.getResources().size()) - 1;
        return this.library.getResources().get(choice);
    }

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

    private void printMediaOptions() {
        int index = 1;
        System.out.println("Media: ");
        for (Media media : this.library.getMedia()) {
            System.out.println(index + ". " + media.getTitle() + ", " + media.getClass().getSimpleName());
            index++;
        }
    }

    private void printResourceOptions() {
        int index = 1;
        System.out.println("Resources: ");
        for (Resource resource : this.library.getResources()) {
            System.out.println(index + ". " + resource.getClass().getSimpleName() + " " + resource.getNumber());
            index++;
        }
    }
}
