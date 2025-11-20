package ca.umanitoba.cs.egilsons.output;

import ca.umanitoba.cs.egilsons.domain.map.*;

/**
 * Prints the information of a {@link Map}.
 */
public class MapPrinter implements Printer {
    private Map map;

    public MapPrinter(final Map map) {
        this.map = map;
    }

    public void print() {
        final char SPACE = ' ';
        final char WALL = '*';
        final char EXIT = 'E';
        final char MAIN_DESK = 'D';
        final char KIOSK = 'K';
        final char FANTASY = 'F';
        final char YOUNG_ADULT = 'Y';
        final char ROMANCE = 'R';
        final char SCIENCE_FICTION = 'S';
        final char CHILDREN = 'C';
        final char HISTORICAL_FICTION = 'T';
        final char NON_FICTION = 'N';
        final char MYSTERY = 'M';
        final char HORROR = 'H';
        final char COMPUTERS = 'P';
        final char ROOM_DOOR = 'O';
        final char PATH = '.';
        final char DESTINATION = '#';

        System.out.println("Legend: ");
        System.out.println("WALLS: " + WALL + "\nENTRANCES/EXITS: " + EXIT + "\nMAIN DESK: " + MAIN_DESK
                + "\nKIOSK: " + KIOSK + "\nFANTASY SECTION: " + FANTASY + "\nYOUNG ADULT SECTION: " + YOUNG_ADULT
                + "\nROMANCE SECTION: " + ROMANCE + "\nSCIENCE FICTION SECTION: " + SCIENCE_FICTION
                + "\nCHILDREN'S SECTION: " + CHILDREN + "\nHISTORICAL FICTION SECTION: " + HISTORICAL_FICTION
                + "\nNON-FICTION: " + NON_FICTION + "\nMYSTERY SECTION: " + MYSTERY + "\nHORROR SECTION: " + HORROR
                + "\nCOMPUTERS: " + COMPUTERS + "\nROOM DOOR: " + ROOM_DOOR + "\nITEM PATH: " + PATH
                + "\nITEM DESTINATION: " + DESTINATION);

        System.out.println("Map: ");
        for (int i = 0; i < this.map.getDisplay().length; i++) {
            for (int j = 0; j < this.map.getDisplay()[i].length; j++) {
                if (this.map.getDisplay()[i][j] == null) {
                    System.out.print(SPACE);
                } else if (this.map.getDisplay()[i][j] == MapType.WALL) {
                    System.out.print(WALL);
                } else if (this.map.getDisplay()[i][j] == MapType.EXIT) {
                    System.out.print(EXIT);
                }else if (this.map.getDisplay()[i][j] == MapType.DESK){
                    System.out.print(MAIN_DESK);
                } else if (this.map.getDisplay()[i][j] == MapType.KIOSK){
                    System.out.print(KIOSK);
                } else if (this.map.getDisplay()[i][j] == MapType.FANTASY_SECTION) {
                    System.out.print(FANTASY);
                } else if (this.map.getDisplay()[i][j] == MapType.YOUNG_ADULT_SECTION) {
                    System.out.print(YOUNG_ADULT);
                } else if (this.map.getDisplay()[i][j] == MapType.ROMANCE_SECTION) {
                    System.out.print(ROMANCE);
                } else if (this.map.getDisplay()[i][j] == MapType.SCIENCE_FICTION_SECTION) {
                    System.out.print(SCIENCE_FICTION);
                } else if (this.map.getDisplay()[i][j] == MapType.CHILDREN_SECTION) {
                    System.out.print(CHILDREN);
                } else if (this.map.getDisplay()[i][j] == MapType.HISTORICAL_FICTION_SECTION) {
                    System.out.print(HISTORICAL_FICTION);
                } else if (this.map.getDisplay()[i][j] == MapType.NON_FICTION_SECTION) {
                    System.out.print(NON_FICTION);
                } else if (this.map.getDisplay()[i][j] == MapType.MYSTERY_SECTION) {
                    System.out.print(MYSTERY);
                } else if (this.map.getDisplay()[i][j] == MapType.HORROR_SECTION) {
                    System.out.print(HORROR);
                } else if (this.map.getDisplay()[i][j] == MapType.COMPUTER) {
                    System.out.print(COMPUTERS);
                } else if (this.map.getDisplay()[i][j] == MapType.ROOM_DOOR) {
                    System.out.print(ROOM_DOOR);
                } else if (this.map.getDisplay()[i][j] == MapType.PATH) {
                    System.out.print(PATH);
                } else {
                    System.out.print(DESTINATION);
                }
            }
            System.out.println();
        }
    }
}
