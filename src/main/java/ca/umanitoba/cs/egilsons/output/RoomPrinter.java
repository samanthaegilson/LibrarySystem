package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.resource.Room;

/**
 * Prints the information of a {@link Room}.
 */
public class RoomPrinter implements Printer {
    private Room room;

    public RoomPrinter(final Room room) {
        this.room = room;
    }

    public void print() {
        System.out.println("Room: ");
        System.out.println("Number: " + this.room.getNumber());
    }
}
