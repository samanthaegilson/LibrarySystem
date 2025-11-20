package ca.umanitoba.cs.egilsons.domain.resource;

import ca.umanitoba.cs.egilsons.domain.map.Coordinates;

/**
 * An interface for resources that belong to a {@link ca.umanitoba.cs.egilsons.domain.Library}.
 */
public interface Resource {
    int getNumber();
    Coordinates getCoordinates();
    Booking getMonthBookings();
}
