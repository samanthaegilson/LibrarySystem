package ca.umanitoba.cs.egilsons.domain.resource;

public class Booking {
    private TimeSlot[][][] monthBookings;

    public Booking() {
        final int WEEKS = 4;
        final int DAYS = 7;
        final int HOURS = 12;
        final int FIRST_START_TIME = 8;
        final int FIRST_END_TIME = 9;
        this.monthBookings = new TimeSlot[WEEKS][DAYS][HOURS];

        for (int i = 0; i < monthBookings[0][0].length; i++) {
            for (int j = 0; j < monthBookings[0].length; j++) {
                for (int k = 0; k < monthBookings.length; k++) {
                    monthBookings[k][j][i] = new TimeSlot(i + FIRST_START_TIME, i + FIRST_END_TIME);
                }
            }
        }
    }

    public TimeSlot[][][] getMonthBookings() {
        return this.monthBookings;
    }
}
