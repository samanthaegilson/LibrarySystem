package ca.umanitoba.cs.egilsons.domain.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * A room. Rooms are a bookable type of {@link Resource}.
 */
public class Room implements Resource {
    private ResourceType type = ResourceType.ROOM;
    private int number;
    private List<Booking> bookings;
    private static int count = 0;

    /**
     * Default constructor for a room. Determines the room number by how
     * many rooms have been created
     */
    public Room () {
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
