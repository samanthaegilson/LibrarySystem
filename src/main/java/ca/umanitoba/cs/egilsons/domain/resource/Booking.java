package ca.umanitoba.cs.egilsons.domain.resource;

import com.google.common.base.Preconditions;

/**
 * A booking. Holds a months worth of {@link TimeSlot}.
 */
public class Booking {
    private TimeSlot[][][] monthBookings;
    private final static int END_WEEK = 4;
    private final static int END_DAY = 7;
    private final static int START_HOUR = 8;
    private final static int END_HOUR = 20;

    private void checkBooking() {
        Preconditions.checkNotNull(monthBookings);

        for (TimeSlot[][] week : monthBookings) {
            for (TimeSlot[] day : week) {
                for (TimeSlot slot : day) {
                    Preconditions.checkNotNull(slot, "Time slots in monthBookings should never be null.");
                }
            }
        }
    }

    /**
     * A default constructor for Booking
     */
    public Booking() {
        final int HOURS = 12;
        final int FIRST_END_TIME = START_HOUR + 1;
        this.monthBookings = new TimeSlot[END_WEEK][END_DAY][HOURS];

        // Initializes time slots for the month
        for (int i = 0; i < monthBookings[0][0].length; i++) {
            for (int j = 0; j < monthBookings[0].length; j++) {
                for (int k = 0; k < monthBookings.length; k++) {
                    monthBookings[k][j][i] = new TimeSlot(i + START_HOUR, i + FIRST_END_TIME,
                            j + 1, k + 1);
                }
            }
        }
        checkBooking();
    }

    public TimeSlot[][][] getMonthBookings() {
        return this.monthBookings;
    }

    public static int getEndWeek() {
        return END_WEEK;
    }

    public static int getEndDay() {
        return END_DAY;
    }

    public static int getStartHour() {
        return START_HOUR;
    }

    public static int getEndHour() {
        return END_HOUR;
    }

    /**
     * Finds the time slot from the week, the day and the start time
     *
     * @param week the week of the time slot
     * @param day the day of the time slot
     * @param startTime the starting time of the time slot
     * @return the time slot
     */
    public TimeSlot getTimeSlot(int week, int day, int startTime) {
        checkBooking();
        TimeSlot selected = null;
        if (week >= 1 && week <= END_WEEK) {
            if (day >= 1 && day <= END_DAY) {
                if (startTime >= START_HOUR && startTime < END_HOUR) {
                    selected = this.monthBookings[week - 1][day - 1][startTime - START_HOUR];
                }
            }
        }
        checkBooking();
        return selected;
    }

    /**
     * Books a time slot from the week, the day and the start time
     *
     * @param week the week of the time slot to book
     * @param day the day of the time slot to book
     * @param startTime the starting time of the time slot to book
     */
    public void book(int week, int day, int startTime) {
        checkBooking();
        if (week >= 1 && week <= END_WEEK) {
            if (day >= 1 && day <= END_DAY) {
                if (startTime >= START_HOUR && startTime < END_HOUR) {
                    this.monthBookings[week - 1][day - 1][startTime - START_HOUR].book();
                }
            }
        }
        checkBooking();
    }
}
