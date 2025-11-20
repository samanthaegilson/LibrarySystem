package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.TimeSlot;
import ca.umanitoba.cs.egilsons.logic.BookResource;

import java.util.List;
import java.util.Scanner;

/**
 * Books a {@link TimeSlot} of a {@link Resource}.
 */
public class BookResourceDisplay {
    private final BookResource bookResource;
    private final Library library;
    private final Scanner keyboard;

    /**
     * A constructor for BookResourceDisplay. Receives the library and the member
     *
     * @param library the library of the resource
     * @param member the member booking the resource
     */
    public BookResourceDisplay(Library library, Member member) {
        this.bookResource = new BookResource(member);
        this.library = library;
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Runs the filtering until a time slot is chosen
     */
    public void bookResourceSlot() {
        boolean running = true;
        while (running) {
            // Choose resource first, then apply filters to that resources times
            System.out.println("Please select a resource to view available time slots: ");
            Resource chosenResource = chooseResource();
            List<TimeSlot> options = filterOptions(chosenResource);
            // Print the slots
            printTimeSlots(options);
            boolean willChoose = chooseOrReset();
            if (willChoose) {
                running = false;
                System.out.println("Please select a time slot: ");
                int slotChoice = getChoice(1, options.size()) - 1;
                this.bookResource.bookResource(options.get(slotChoice), chosenResource);
                System.out.println(chosenResource.getClass().getSimpleName() + " " + chosenResource.getNumber()
                        + " booked week " + options.get(slotChoice).getWeek() + ", day "
                        + options.get(slotChoice).getDay() + ", from " + options.get(slotChoice).getStartHour()
                        + " to " + options.get(slotChoice).getEndHour());
            }
        }
    }

    /**
     * Filters the available time slots
     *
     * @param resource the resource of the time slots
     * @return a list of filtered time slots
     */
    private List<TimeSlot> filterOptions(Resource resource) {
        List<TimeSlot> options;

        // Pick a week
        System.out.println("Please enter the week of time slots you would like to view (1-4): ");
        int week = getChoice(resource.getMonthBookings().getStartWeek(), resource.getMonthBookings().getEndWeek());
        System.out.println("""
                Would you like to view the full week or filter the day?
                1. VIEW FULL WEEK
                2. FILTER DAY""");
        int filterChoice = getChoice(1, 2); // There are only two options
        if (filterChoice == 1) {
            // Shows the full week
            options = this.bookResource.filterWeek(week, resource);
        } else {
            // Pick a day
            System.out.println("Please enter the day of time slots you would like to view (1-7): ");
            int day = getChoice(resource.getMonthBookings().getStartDay(), resource.getMonthBookings().getEndDay());
            System.out.println("""
                    Would you like to view the full day or filter the time?
                    1. VIEW FULL DAY
                    2. FILTER TIME""");
            filterChoice = getChoice(1, 2); // There are only two options
            if (filterChoice == 1) {
                // Shows the full day
                options = this.bookResource.filterDay(week, day, resource);
            } else {
                // Pick a starting time
                final int MAX_SLOTS = 336; // The total amount of time slots in the month
                System.out.println("Please enter the start time of time slots you would like to view (8-19): ");
                int startTime = getChoice(resource.getMonthBookings().getStartHour(), resource.getMonthBookings().getEndHour() - 1);

                // Pick the amount of slots to show after the starting time
                System.out.println("Please enter the amount of time slots you would like to view after " + startTime
                        + ": ");
                int amount = getChoice(1, MAX_SLOTS);
                options = this.bookResource.filterAmount(week, day, startTime, amount, resource);
                if (options.size() < amount) {
                    System.out.println("We are unable to show you the amount of time slots you requested as they go "
                            + " past the current month.");
                }
            }
        }
        return options;
    }

    /**
     * Gets the user's choice of choosing a time slot to book or reset the filters
     *
     * @return if the user will choose a time slot or not
     */
    private boolean chooseOrReset() {
        boolean willChoose = false;
        System.out.println("""
                Would you like to choose a time slot or reset the filters:
                1. CHOOSE TIME SLOT
                2. RESET FILTERS""");
        int choice = getChoice(1, 2); // There are only two options
        if (choice == 1) {
            willChoose = true;
        }
        return willChoose;
    }

    /**
     * Prints the filtered time slots
     *
     * @param slots the time slots to print
     */
    private void printTimeSlots(List<TimeSlot> slots) {
        System.out.println("Available slots: ");
        int week = slots.get(0).getWeek();
        int day = slots.get(0).getDay();
        System.out.println("Week " + week + ":");
        System.out.println("Day " + day + ":");
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getWeek() != week) {
                week = slots.get(i).getWeek();
                System.out.println("Week " + week + ":");
            }
            if (slots.get(i).getDay() != day) {
                day = slots.get(i).getDay();
                System.out.println("Day " + day + ":");
            }
            System.out.println((i + 1) + ". " + slots.get(i).getStartHour() + "-" + slots.get(i).getEndHour());
        }
    }

    /**
     * Prints the resources to choose from
     */
    private void printResourceOptions() {
        int index = 1;
        System.out.println("Resources: ");
        for (Resource resource : this.library.getResources()) {
            System.out.println(index + ". " + resource.getClass().getSimpleName() + " " + resource.getNumber());
            index++;
        }
    }

    /**
     * Gets the user's choice of resource
     *
     * @return the chosen resource
     */
    private Resource chooseResource() {
        printResourceOptions();
        int choice = getChoice(1, this.library.getResources().size()) - 1;
        return this.library.getResources().get(choice);
    }

    /**
     * Gets an integer from the user between 1 and a high bound.
     *
     * @param high the highest the choice can be
     * @return the integer representing the choice of the user
     */
    private int getChoice(int low, int high) {
        boolean valid = false;
        int choice = -1;
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                choice = Integer.parseInt(input);
                // The choice is only valid if it is an integer between the high and low bounds
                if (choice >= low && choice <= high) {
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
