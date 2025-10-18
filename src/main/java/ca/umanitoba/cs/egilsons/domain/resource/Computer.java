package ca.umanitoba.cs.egilsons.domain.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * A computer. Computers are a bookable type of {@link Resource}.
 */
public class Computer implements Resource {
    private ResourceType type = ResourceType.COMPUTER;
    private int number;
    private List<Booking> bookings;
    private static int count = 0;

    /**
     * Default constructor for a computer. Determines the computer number by how
     * many computers have been created
     */
    public Computer() {
        count++;
        this.bookings = new ArrayList<>();
        this.number = count;
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
