package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Member;
import ca.umanitoba.cs.egilsons.domain.resource.Booking;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.TimeSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic for booking a {@link Resource}.
 */
public class BookResource {
    private final Member member;

    /**
     * A constructor for BookResource. Receives the member
     *
     * @param member the member booking the resource
     */
    public BookResource(Member member) {
        this.member = member;
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
     */
    public List<TimeSlot> filterAmount(int week, int day, int startTime, int amount, Resource resource) {
        List<TimeSlot> filtered = new ArrayList<>();
        Booking booking = resource.getMonthBookings();
        for (int i = 0; i < amount; i++) {
            // For if the time goes past the end of the day
            if (startTime >= booking.getEndHour()){
                day++;
                startTime = booking.getStartHour();
                if (day > booking.getEndDay()) {
                    week++;
                    day = booking.getStartDay();
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
        return filtered;
    }

    /**
     * Filters available time slots for a certain week
     *
     * @param week the week of the time slots
     * @param resource the resource the time slots belong to
     * @return a list of time slots
     */
    public List<TimeSlot> filterWeek(int week, Resource resource) {
        int weekIndex = week - 1;
        List<TimeSlot> filtered = new ArrayList<>();
        Booking booking = resource.getMonthBookings();

        final int START_HOUR = booking.getStartHour();
        for (int i = 0; i < booking.getMonthBookings()[weekIndex].length; i++) {
            for (int j = 0; j < booking.getMonthBookings()[weekIndex][i].length; j++) {
                if (!booking.getTimeSlot(week, i + 1, j + START_HOUR).isBooked()) {
                    filtered.add(booking.getTimeSlot(week, i + 1, j + START_HOUR));
                }
            }
        }
        return filtered;
    }

    /**
     * Filters available time slots for a certain day
     *
     * @param week the week of the time slots
     * @param day the day of the time slots
     * @param resource the resource the time slots belong to
     * @return a list of time slots
     */
    public List<TimeSlot> filterDay(int week, int day, Resource resource) {
        int weekIndex = week - 1;
        int dayIndex = day - 1;
        List<TimeSlot> filtered = new ArrayList<>();
        Booking booking = resource.getMonthBookings();

        final int START_HOUR = booking.getStartHour();
        for (int i = 0; i < booking.getMonthBookings()[weekIndex][dayIndex].length; i++) {
            if (!booking.getTimeSlot(week, day, i + START_HOUR).isBooked()) {
                filtered.add(booking.getTimeSlot(week, day, i + START_HOUR));
            }
        }
        return filtered;
    }

    /**
     * Books a time slot for a resource
     *
     * @param slot the time slot to book
     * @param resource the resource the time slot belongs to
     */
    public void bookResource(TimeSlot slot, Resource resource) {
        this.member.bookResource(slot, resource);
    }
}
