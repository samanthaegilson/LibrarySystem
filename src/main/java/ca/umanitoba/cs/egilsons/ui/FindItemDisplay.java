package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.logic.FindItem;
import ca.umanitoba.cs.egilsons.output.MapPrinter;

import java.util.Scanner;

/**
 * Finds a path to an item in the {@link Library} on a {@link Map}.
 */
public class FindItemDisplay {
    private final FindItem findItem;
    private final Library library;
    private final Scanner keyboard;

    /**
     * A constructor for FindItemDisplay. Receives the library
     *
     * @param library the library with the items
     */
    public FindItemDisplay(Library library) {
        this.findItem = new FindItem(library);
        this.library = library;
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Gets the choice of item to find
     */
    public void enterItem() {
        Map path;

        boolean isMedia = findItemType();
        if (isMedia) {
            Media media = chooseMedia();
            path = findItem.findItemPath(media.getCoordinates());
        } else {
            Resource resource = chooseResource();
            path = findItem.findItemPath(resource.getCoordinates());
        }

        MapPrinter mapPrinter = new MapPrinter(path);
        mapPrinter.print();
    }

    /**
     * Gets the user's choice between media or resource
     *
     * @return if the user is looking for media or not
     */
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

    /**
     * Chooses which media to find
     *
     * @return the media to find
     */
    private Media chooseMedia() {
        Media chosenMedia;
        printMediaOptions();
        // Gets choice of media
        System.out.println("Please select the media you are looking for: ");
        int mediaChoice = getChoice(this.library.getMedia().size()) - 1;
        chosenMedia = this.library.getMedia().get(mediaChoice);
        return chosenMedia;
    }

    /**
     * Chooses which resource to find
     *
     * @return the resource to find
     */
    private Resource chooseResource() {
        printResourceOptions();
        int choice = getChoice(this.library.getResources().size()) - 1;
        return this.library.getResources().get(choice);
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

    /**
     * Prints all media in the library
     */
    private void printMediaOptions() {
        int index = 1;
        System.out.println("Media: ");
        for (Media media : this.library.getMedia()) {
            System.out.println(index + ". " + media.getTitle() + ", " + media.getClass().getSimpleName());
            index++;
        }
    }

    /**
     * Prints all the resources in the library
     */
    private void printResourceOptions() {
        int index = 1;
        System.out.println("Resources: ");
        for (Resource resource : this.library.getResources()) {
            System.out.println(index + ". " + resource.getClass().getSimpleName() + " " + resource.getNumber());
            index++;
        }
    }
}
