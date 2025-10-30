package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.resource.Computer;

/**
 * Prints the information of a {@link Computer}.
 */
public class ComputerPrinter implements Printer{
    private Computer computer;

    public ComputerPrinter(final Computer computer) {
        this.computer = computer;
    }

    public void print() {
        System.out.println("Computer: ");
        System.out.println("Number: " + this.computer.getNumber());
    }
}
