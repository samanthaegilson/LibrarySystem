package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;

/**
 * Prints the information of a {@link Library}.
 */
public class LibraryPrinter implements Printer {
    private Library library;

    public LibraryPrinter(final Library library) {
        this.library = library;
    }

    public void print() {
        System.out.println(this.library.getName() + " library");

        System.out.println("Media: ");
        for (final Media media : this.library.getMedia()) {
            new MediaPrinter(media).print();
        }

        System.out.println("Resources: ");
        for (final Resource resource : this.library.getResources()) {
            System.out.println(resource.getType() + " " + resource.getNumber());
        }

        System.out.println("Map: ");
        MapPrinter mapPrinter = new MapPrinter(this.library.getMap());
        mapPrinter.print();
    }
}
