package ca.umanitoba.cs.egilsons.domain.resource;

public class Booking {
    private TimeSlot[][] weekBookings;

    public Booking() {
        final int FIRST_START_TIME = 8;
        final int FIRST_END_TIME = 9;
        this.weekBookings = new TimeSlot[7][12];

        for (int i = 0; i < weekBookings[0].length; i++) {
            for (int j = 0; j < weekBookings.length; j++) {
                weekBookings[j][i] = new TimeSlot(i + FIRST_START_TIME, i + FIRST_END_TIME);
            }
        }
    }
}
