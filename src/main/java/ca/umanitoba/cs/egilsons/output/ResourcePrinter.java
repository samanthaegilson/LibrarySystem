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
        System.out.println("Resource: ");
        System.out.println("Type: " + this.resource.getType());
        System.out.println("Number: " + this.resource.getNumber());
    }
}
