package ca.umanitoba.cs.egilsons.domain;

import java.util.ArrayList;
import java.util.List;

public interface Resource {
    public boolean bookTime(Booking booking);
    public ResourceType getType();
    public int getNumber();
    public List<Booking> getBookings();
}
