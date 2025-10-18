package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.Map;

/**
 * Prints the information of a {@link Map}.
 */
public class MapPrinter implements Printer {
    private Map map;

    public MapPrinter(final Map map) {
        this.map = map;
    }

    public void print() {
        System.out.println("Legend: ");
        for (int i = 0; i < map.getLegend().length; i++) {
            System.out.println(map.getLegend()[i]);
        }
        System.out.println("Map: ");
        for (int i = 0; i < map.getDisplay().length; i++) {
            for (int j = 0; j < map.getDisplay()[i].length; j++) {
                System.out.print(map.getDisplay()[i][j]);
            }
            System.out.println();
        }
    }
}
