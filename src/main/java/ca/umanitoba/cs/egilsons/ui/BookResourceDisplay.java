package ca.umanitoba.cs.egilsons.ui;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.resource.Booking;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.TimeSlot;
import ca.umanitoba.cs.egilsons.logic.BookResource;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidDayException;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidHourException;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidSlotAmountException;
import ca.umanitoba.cs.egilsons.logic.exceptions.InvalidWeekException;

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
                int slotChoice = getChoice(options.size()) - 1;
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
        List<TimeSlot> options = null;
        int week = -1;
        while (week == -1) {
            // Pick a week
            week = selectWeek();
            try {
                // Filter the week
                options = this.bookResource.filterWeek(week, resource);
            } catch (InvalidWeekException e) {
                System.out.println(week + " is not a valid week; select from 1-" + Booking.getEndWeek());
                week = -1;
            }
        }

        System.out.println("""
                Would you like to view the full week or filter the day?
                1. VIEW FULL WEEK
                2. FILTER DAY""");
        int filterChoice = getChoice(2); // There are only two options

        if (filterChoice == 2) {
            int day = -1;
            while (day == -1) {
                // Pick a day
                day = selectDay();
                try {
                    // Filter day
                    options = this.bookResource.filterDay(week, day, resource);
                } catch (InvalidWeekException e) {
                    System.out.println(week + " is not a valid week; select from 1-" + Booking.getEndWeek());
                } catch (InvalidDayException e) {
                    System.out.println(day + " is not a valid day; select from 1-" + Booking.getEndDay());
                    day = -1;
                }
            }

            System.out.println("""
                    Would you like to view the full day or filter the time?
                    1. VIEW FULL DAY
                    2. FILTER TIME""");
            filterChoice = getChoice(2); // There are only two options

            if (filterChoice == 2) {
                // The total amount of time slots in the month
                final int MAX_SLOTS = Booking.getEndWeek() * Booking.getEndDay() * (Booking.getEndHour() - Booking.getStartHour());

                int startTime = -1;
                int amount = -1;
                while (startTime == -1 || amount == -1) {
                    // Pick a starting time
                    startTime = selectStartTime();
                    // Pick the amount of slots to show after the starting time
                    amount = selectSlotAmount();
                    try {
                        // Filter by amount after starting time
                        options = this.bookResource.filterAmount(week, day, startTime, amount, resource);
                    } catch (InvalidWeekException e) {
                        System.out.println(week + " is not a valid week; select from 1-" + Booking.getEndWeek());
                    } catch (InvalidDayException e) {
                        System.out.println(day + " is not a valid day; select from 1-" + Booking.getEndDay());
                    } catch (InvalidHourException e) {
                        System.out.println(startTime + " is not a valid day; select from " + Booking.getStartHour()
                                + "-" + (Booking.getEndHour() - 1));
                        startTime = -1;
                    } catch (InvalidSlotAmountException e) {
                        System.out.println(amount + " is not a valid amount; select from 1-" + MAX_SLOTS);
                        amount = -1;
                    }
                }
                if (options.size() < amount) {
                    System.out.println("We are unable to show you the amount of time slots you requested as they go "
                            + " past the current month.");
                }
            }
        }
        return options;
    }

    /**
     * Gets the user's choice of a week
     *
     * @return the selected week
     */
    private int selectWeek() {
        boolean valid = false;
        int week = -1;
        System.out.println("Please enter the week of time slots you would like to view (1-" + Booking.getEndWeek()
                + "): ");
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                week = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Selected week must be a positive whole number, e.g., 1");
            }
        }
        return week;
    }

    /**
     * Gets the user's choice of a day
     *
     * @return the selected day
     */
    private int selectDay() {
        boolean valid = false;
        int day = -1;
        System.out.println("Please enter the day of time slots you would like to view (1-" + Booking.getEndDay()
                + "): ");
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                day = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Selected day must be a positive whole number, e.g., 1");
            }
        }
        return day;
    }

    /**
     * Gets the user's choice of a starting time
     *
     * @return the selected start time
     */
    private int selectStartTime() {
        boolean valid = false;
        int startTime = -1;
        System.out.println("Please enter the start time of time slots you would like to view ("
                + Booking.getStartHour() + "-" + (Booking.getEndHour() - 1) + "): ");
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                startTime = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Selected start time must be a positive whole number, e.g., 8");
            }
        }
        return startTime;
    }

    /**
     * Gets the user's choice for an amount of time slots
     *
     * @return the chosen amount of time slots
     */
    private int selectSlotAmount() {
        boolean valid = false;
        int amount = -1;
        System.out.println("Please enter the amount of time slots you would like to view after the start"
                + " time: ");
        while (!valid) {
            String input = this.keyboard.nextLine();
            try {
                amount = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Time slot amount must be a positive whole number, e.g., 8");
            }
        }
        return amount;
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
        int choice = getChoice(2); // There are only two options
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
                    System.out.println("Not a valid choice. Must be a number between 1 and " + high
                            + ", e.g., 1.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Not a number, please enter a number between 1 and " + high  + ", e.g., 1.");
            }
        }
        return choice;
    }
}
