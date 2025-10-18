package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;

/**
 * Prints the information of a {@link Resource}.
 */
public class ResourcePrinter implements Printer {
    private Resource resource;

    public ResourcePrinter(final Resource resource) {
        this.resource = resource;
    }

    public void print() {
        System.out.println(this.resource.getType() + " " + this.resource.getNumber());
        System.out.println("Bookings: ");
        for (int i = 0; i < this.resource.getBookings().size(); i++) {
            System.out.println(this.resource.getBookings().get(i).startHour() + ":"
                    + this.resource.getBookings().get(i).startMinute() + " - "
                    + this.resource.getBookings().get(i).endHour() + ":"
                    + this.resource.getBookings().get(i).endMinute());
        }
    }
}
