package ca.umanitoba.cs.egilsons.domain;

import java.util.ArrayList;
import java.util.List;

public class Room implements Resource {
    private ResourceType type = ResourceType.ROOM;
    private int number;
    private List<Booking> bookings;
    private static int count = 0;

    public Room () {
        this.bookings = new ArrayList<>();
        this.number = count;
        count++;
    }

    @Override
    public boolean bookTime(Booking booking) {
        boolean available = true;
        for (int i = 0; i < this.bookings.size(); i++) {
            if (this.bookings.get(i).startHour() <= booking.startHour()
                    && this.bookings.get(i).endHour() >= booking.endHour()
                    && this.bookings.get(i).startMinute() <= booking.startMinute()
                    && this.bookings.get(i).endMinute() >= booking.endMinute()) {
                available = false;
            }
        }

        if (available) {
            this.bookings.add(booking);
        }

        return available;
    }

    public ResourceType getType() {
        return this.type;
    }

    public int getNumber() {
        return this.number;
    }

    public List<Booking> getBookings() {
        return this.bookings;
    }
}
