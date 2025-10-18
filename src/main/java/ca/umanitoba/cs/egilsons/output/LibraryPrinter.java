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
        System.out.println(library.getName() + " library");
        System.out.println("Media: ");
        for (final Media m : library.getMedia()) {
            new MediaPrinter(m).print();
        }
        System.out.println("Resources: ");
        for (final Resource r : library.getResources()) {
            new ResourcePrinter(r).print();
        }
    }
}
