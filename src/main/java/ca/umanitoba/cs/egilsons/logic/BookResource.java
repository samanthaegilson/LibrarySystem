package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.LibrarySystem;
import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.resource.Booking;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.TimeSlot;
import ca.umanitoba.cs.egilsons.logic.exceptions.*;
import ca.umanitoba.cs.egilsons.persistence.LibrarySystemPersistence;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic for booking a {@link Resource}.
 */
public class BookResource {
    private LibrarySystem librarySystem;
    private final Member member;
    private LibrarySystemPersistence persistence;

    /**
     * Checking that BookResource is in a valid state
     */
    private void checkBookResource() {
        Preconditions.checkNotNull(member, "Member should never be null.");
    }

    /**
     * A constructor for BookResource. Receives the library and the member
     *
     * @param librarySystem the library system of the resource
     * @param member the member booking the resource
     * @param persistence the persistence of the library system
     */
    public BookResource(LibrarySystem librarySystem, Member member, LibrarySystemPersistence persistence) {
        this.librarySystem = librarySystem;
        this.member = member;
        this.persistence = persistence;
        checkBookResource();
    }

    /**
     * Filters a certain amount of available time slots after a specific time
     *
     * @param week the week of the first time slot
     * @param day the day of the first time slot
     * @param startTime the start time of the first time slot
     * @param amount the amount of time slots to show
     * @param resource the resource the time slots belong to
     * @return a list of time slots
     * @throws InvalidWeekException if the week is out of range
     * @throws InvalidDayException if the day is out of range
     * @throws InvalidHourException if the start time is out of range
     * @throws InvalidSlotAmountException if the amount is out of range
     */
    public List<TimeSlot> filterAmount(int week, int day, int startTime, int amount, Resource resource) throws
            InvalidWeekException, InvalidDayException, InvalidHourException, InvalidSlotAmountException {
        checkBookResource();
        Preconditions.checkNotNull(resource, "Resource should not be null.");

        List<TimeSlot> filtered = new ArrayList<>();
        Booking booking = resource.getMonthBookings();
        // The total amount of time slots in the month
        final int MAX_SLOTS = Booking.getEndWeek() * Booking.getEndDay() * (Booking.getEndHour() - Booking.getStartHour());

        if (week < 1 || week > Booking.getEndWeek()) {
            throw new InvalidWeekException();
        }

        if (day < 1 || day > Booking.getEndDay()) {
            throw new InvalidDayException();
        }

        if (startTime < Booking.getStartHour() || startTime > Booking.getEndHour() - 1) {
            throw new InvalidHourException();
        }

        if (amount < 1 || amount > MAX_SLOTS) {
            throw new InvalidSlotAmountException();
        }

        for (int i = 0; i < amount; i++) {
            // For if the time goes past the end of the day
            if (startTime >= Booking.getEndHour()){
                day++;
                startTime = Booking.getStartHour();
                if (day > Booking.getEndDay()) {
                    week++;
                    day = 1;
                }
            }

            // Ensures the time slot is part of the month
            if (booking.getTimeSlot(week, day, startTime) != null) {
                if (!booking.getTimeSlot(week, day, startTime).isBooked()) {
                    filtered.add(booking.getTimeSlot(week, day, startTime));
                } else {
                    i--;
                }
            }

            startTime++;
        }

        checkBookResource();
        return filtered;
    }

    /**
     * Filters available time slots for a certain week
     *
     * @param week the week of the time slots
     * @param resource the resource the time slots belong to
     * @return a list of time slots
     * @throws InvalidWeekException if the week is out of range
     */
    public List<TimeSlot> filterWeek(int week, Resource resource) throws InvalidWeekException {
        checkBookResource();
        Preconditions.checkNotNull(resource, "Resource should not be null.");

        if (week < 1 || week > Booking.getEndWeek()) {
            throw new InvalidWeekException();
        }

        int weekIndex = week - 1;
        List<TimeSlot> filtered = new ArrayList<>();
        Booking booking = resource.getMonthBookings();

        // Filters the timeslots for the week
        for (int i = 0; i < booking.getMonthBookings()[weekIndex].length; i++) {
            for (int j = 0; j < booking.getMonthBookings()[weekIndex][i].length; j++) {
                if (!booking.getTimeSlot(week, i + 1, j + Booking.getStartHour()).isBooked()) {
                    filtered.add(booking.getTimeSlot(week, i + 1, j + Booking.getStartHour()));
                }
            }
        }

        checkBookResource();
        return filtered;
    }

    /**
     * Filters available time slots for a certain day
     *
     * @param week the week of the time slots
     * @param day the day of the time slots
     * @param resource the resource the time slots belong to
     * @return a list of time slots
     * @throws InvalidWeekException if the week is out of range
     * @throws InvalidDayException if the day is out of range
     */
    public List<TimeSlot> filterDay(int week, int day, Resource resource) throws InvalidWeekException, InvalidDayException {
        checkBookResource();
        Preconditions.checkNotNull(resource, "Resource should not be null.");

        if (week < 1 || week > Booking.getEndWeek()) {
            throw new InvalidWeekException();
        }

        if (day < 1 || day > Booking.getEndDay()) {
            throw new InvalidDayException();
        }

        int weekIndex = week - 1;
        int dayIndex = day - 1;
        List<TimeSlot> filtered = new ArrayList<>();
        Booking booking = resource.getMonthBookings();

        // Filters the time slots for the day of the week
        for (int i = 0; i < booking.getMonthBookings()[weekIndex][dayIndex].length; i++) {
            if (!booking.getTimeSlot(week, day, i + Booking.getStartHour()).isBooked()) {
                filtered.add(booking.getTimeSlot(week, day, i + Booking.getStartHour()));
            }
        }

        checkBookResource();
        return filtered;
    }

    /**
     * Books a time slot for a resource
     *
     * @param slot the time slot to book
     * @param resource the resource the time slot belongs to
     */
    public void bookResource(TimeSlot slot, Resource resource) {
        checkBookResource();
        Preconditions.checkNotNull(slot, "Time slot should not be null.");
        Preconditions.checkNotNull(resource, "Resource should not be null.");
        this.member.bookResource(slot, resource);
        this.persistence.saveLibrarySystem(this.librarySystem);
        checkBookResource();
        Preconditions.checkState(slot.isBooked(), "Resource slot should be booked.");
    }
}
