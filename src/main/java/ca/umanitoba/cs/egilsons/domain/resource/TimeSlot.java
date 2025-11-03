package ca.umanitoba.cs.egilsons.domain.resource;

public class TimeSlot {
    // 8am - 8pm, 12 1 hour time slots
    private int startHour;
    private int endHour;
    private boolean booked;

    public TimeSlot(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.booked = false;
    }
}
