package ca.umanitoba.cs.egilsons.domain.resource;

import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidDayException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidEndHourException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidStartHourException;
import ca.umanitoba.cs.egilsons.domain.exceptions.InvalidWeekException;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import com.google.common.base.Preconditions;

/**
 * A time slot. An hour block of time for a {@link Resource} that a {@link ca.umanitoba.cs.egilsons.domain.Member}
 * can book.
 */
public class TimeSlot {
    private final int startHour;
    private final int endHour;
    private final int day;
    private final int week;
    private boolean booked;

    /**
     * Invariant properties for TimeSlot
     */
    private void checkTimeSlot() {
        Preconditions.checkState(startHour >= 8 && startHour < 20,
                "Start hour must be between 8am and 7pm.");
        Preconditions.checkState(endHour > 8 && endHour <= 20,
                "End hour must be between 9am and 8pm.");
        Preconditions.checkState(day >= 1 && day <= 7, "Day of the week must be between 1 and 7.");
        Preconditions.checkState(week >= 1 && week <= 4,
                "Week of the month must be between 1 and 4.");
    }

    /**
     * Constructor for a time slot. Receives the start and end hours, day and week of the time slot
     *
     * @param startHour the starting hour of the time slot
     * @param endHour the end hour of the time slot
     * @param day the day of the time slot
     * @param week the week of the time slot
     */
    private TimeSlot(int startHour, int endHour, int day, int week) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
        this.week = week;
        this.booked = false;
        checkTimeSlot();
    }

    /**
     * Builder class for a time slot
     */
    public static class TimeSlotBuilder {
        private int startHour;
        private int endHour;
        private int day;
        private int week;

        /**
         * Checks that the start hour for a time slot is valid
         *
         * @param startHour the start hour of the time slot
         * @return the time slot builder
         * @throws InvalidStartHourException if the start hour is outside the bounds
         */
        public TimeSlotBuilder startHour(int startHour) throws InvalidStartHourException {
            if (startHour < Booking.getStartHour() || startHour > Booking.getEndHour() - 1) {
                throw new InvalidStartHourException();
            }

            this.startHour = startHour;
            return this;
        }

        /**
         * Checks that the end hour for a time slot is valid
         *
         * @param endHour the end hour of the time slot
         * @return the time slot builder
         * @throws InvalidEndHourException if the end hour is outside the bounds
         */
        public TimeSlotBuilder endHour(int endHour) throws InvalidEndHourException {
            if (endHour < Booking.getStartHour() + 1 || endHour > Booking.getEndHour()) {
                throw new InvalidEndHourException();
            }

            this.endHour = endHour;
            return this;
        }

        /**
         * Checks that the day for a time slot is valid
         *
         * @param day the day of the time slot
         * @return the time slot builder
         * @throws InvalidDayException if the day is outside the bounds
         */
        public TimeSlotBuilder day(int day) throws InvalidDayException {
            if (day < 1 || day > Booking.getEndDay()) {
                throw new InvalidDayException();
            }

            this.day = day;
            return this;
        }

        /**
         * Checks that the week for a time slot is valid
         *
         * @param week the week of the time slot
         * @return the time slot builder
         * @throws InvalidWeekException if the week is outside the bounds
         */
        public TimeSlotBuilder week(int week) throws InvalidWeekException {
            if (week < 1 || week > Booking.getEndWeek()) {
                throw new InvalidWeekException();
            }

            this.week = week;
            return this;
        }

        /**
         * Creates a time slot
         *
         * @return the time slot
         */
        public TimeSlot build() {
            return new TimeSlot(this.startHour, this.endHour, this.day, this.week);
        }
    }

    public int getStartHour() {
        return this.startHour;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public int getDay() {
        return this.day;
    }

    public int getWeek() {
        return this.week;
    }

    public boolean isBooked() {
        return this.booked;
    }

    /**
     * Reserves the time slot
     *
     * @return if the time slot was successfully booked or not
     */
    public boolean book() {
        boolean booked = false;
        if (!isBooked()) {
            this.booked = true;
            booked = true;
        }
        return booked;
    }
}
