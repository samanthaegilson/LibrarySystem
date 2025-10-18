package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.media.Media;

/**
 * Prints the information of a {@link Media}.
 */
public class MediaPrinter implements Printer {
    private Media media;

    public MediaPrinter(final Media media) {
        this.media = media;
    }

    public void print() {
        System.out.println("Media name: " + this.media.getName());
        System.out.println("Creator: " + this.media.getCreator());
        System.out.println("Format: " + this.media.getFormat());
        System.out.println("Category: " + this.media.getCategory());
        System.out.println("Copies available: " + this.media.getCopies());
        System.out.println("Waitlist: ");
        for (int i = 0; i < this.media.getWaitlist().size(); i++) {
            System.out.println((i + 1) + ". " + this.media.getWaitlist().get(i).getName());
        }
        // Add reviews
    }
}
