package ca.umanitoba.cs.egilsons.domain.resource;

import com.google.common.base.Preconditions;

/**
 * A time slot. An hour block of time for a {@link Resource} that a {@link ca.umanitoba.cs.egilsons.domain.Member}
 * can book.
 */
public class TimeSlot {
    // 8am - 8pm, 12 1 hour time slots
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
    public TimeSlot(int startHour, int endHour, int day, int week) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
        this.week = week;
        this.booked = false;
        checkTimeSlot();
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
