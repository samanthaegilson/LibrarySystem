package ca.umanitoba.cs.egilsons.domain.resource;

import com.google.common.base.Preconditions;

/**
 * A room. Rooms are a bookable type of {@link Resource}.
 */
public class Room implements Resource {
    private final int number;
    private static int count = 0;

    /**
     * Invariant properties for Room
     */
    private void checkRoom() {
        Preconditions.checkState(number > 0, "Number should be bigger than 0.");
        Preconditions.checkState(count >= 0, "Count should never be below 0.");
    }

    /**
     * Default constructor for a room. Determines the room number by how
     * many rooms have been created
     */
    public Room () {
        count++;
        this.number = count;
        checkRoom();
    }

    public int getNumber() {
        return this.number;
    }
}
