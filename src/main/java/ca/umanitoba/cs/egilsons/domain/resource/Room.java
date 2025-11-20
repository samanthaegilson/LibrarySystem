package ca.umanitoba.cs.egilsons.domain.resource;

import ca.umanitoba.cs.egilsons.domain.map.Coordinates;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import com.google.common.base.Preconditions;

/**
 * A room. Rooms are a bookable type of {@link Resource}.
 */
public class Room implements Resource {
    private final int number;
    private Booking monthBookings;
    private static int count = 0;
    private final Coordinates coordinates;

    /**
     * Invariant properties for Room
     */
    private void checkRoom() {
        Preconditions.checkState(number > 0, "Number should be bigger than 0.");
        Preconditions.checkNotNull(monthBookings, "Month bookings should never be null.");
        Preconditions.checkState(count >= 0, "Count should never be below 0.");
        Preconditions.checkNotNull(coordinates, "Coordinates should never be null.");
    }

    /**
     * Default constructor for a room. Determines the room number by how
     * many rooms have been created
     */
    public Room () {
        count++;
        this.number = count;
        this.monthBookings = new Booking();
        this.coordinates = Map.setResourceCoordinates(this);
        checkRoom();
    }

    public int getNumber() {
        return this.number;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public Booking getMonthBookings() {
        return this.monthBookings;
    }
}
