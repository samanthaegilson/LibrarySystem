package ca.umanitoba.cs.egilsons.domain.resource;

import ca.umanitoba.cs.egilsons.domain.map.Coordinates;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import com.google.common.base.Preconditions;

/**
 * A computer. Computers are a bookable type of {@link Resource}.
 */
public class Computer implements Resource {
    private final int number;
    private Booking monthBookings;
    private static int count = 0;
    private Coordinates coordinates;

    /**
     * Invariant properties for Computer
     */
    private void checkComputer() {
        Preconditions.checkState(number > 0, "Number should be bigger than 0.");
        Preconditions.checkState(count >= 0, "Count should never be below 0.");
    }

    /**
     * Default constructor for a computer. Determines the computer number by how
     * many computers have been created
     */
    public Computer() {
        count++;
        this.number = count;
        this.monthBookings = new Booking();
        this.coordinates = Map.setResourceCoordinates(this);
        checkComputer();
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
