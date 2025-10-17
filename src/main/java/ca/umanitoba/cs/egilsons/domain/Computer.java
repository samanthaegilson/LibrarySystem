package ca.umanitoba.cs.egilsons.domain;

import java.util.ArrayList;
import java.util.List;

public class Computer implements Resource {
    private ResourceType type = ResourceType.COMPUTER;
    private int number;
    private List<Booking> bookings;
    private static int count = 0;

    public Computer() {
        this.bookings = new ArrayList<>();
        this.number = count;
        count++;
    }

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
