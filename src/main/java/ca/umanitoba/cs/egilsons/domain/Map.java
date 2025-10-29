package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.resource.ResourceType;
import com.google.common.base.Preconditions;

/**
 * A map. There is one map for every {@link Library} that shows where everything is.
 */
public class Map {
    private char[][] display;
    private String[] legend;

    /**
     * Invariant properties for Map
     */
    private void checkMap() {
        Preconditions.checkNotNull(display, "Display should never be null.");
        Preconditions.checkNotNull(legend, "Legend should never be null.");

        for (String entry : legend) {
            Preconditions.checkNotNull(entry, "Entries in legend should never be null");
            Preconditions.checkState(entry.length() >= 1, "Entries in legend should have at"
                    + "least 1 symbol.");
        }
    }

    /**
     * Constructor of a map. Receive input for the library
     *
     * @param library the library the map is displaying
     */
    public Map(Library library) {
        // Constants for the characters on the map
        final char SPACE = ' ';
        final char HORIZONTAL_WALL = '-';
        final char VERTICAL_WALL = '|';
        final char EXIT = 'E';
        final char MAIN_DESK = 'D';
        final char FANTASY = 'F';
        final char YOUNG_ADULT = 'Y';
        final char ROMANCE = 'R';
        final char SCIENCE_FICTION = 'S';
        final char CHILDREN = 'C';
        final char HISTORICAL_FICTION = 'T';
        final char NON_FICTION = 'N';
        final char MYSTERY = 'M';
        final char HORROR = 'H';
        final char COMPUTERS = 'C';
        final char DOOR = 'O';
        final char KIOSK = 'K';
        // Constants for spacing
        final int LEGEND_ESSENTIALS = 13;
        final int VERTICAL = 10;
        final int REGULAR_HORIZONTAL = 36;
        final int BOOKSHELF = 10;
        final int BOOKSHELF_ROW1 = 3;
        final int BOOKSHELF_ROW2 = 5;
        final int BOOKSHELF_ROW3 = 7;
        final int BOOKSHELF1_SPACE = 2;
        final int BOOKSHELF2_SPACE = 3;
        final int BOOKSHELF3_SPACE = 4;
        final int HORIZONTAL_EXIT = 9;
        final int VERTICAL_EXIT = 4;
        final int DESK_START = 13;
        final int DESK_LENGTH = 3;
        final int RESOURCE_START = 35;
        final int COMPUTER_ROW_1 = 6;
        final int COMPUTER_ROW_2 = 8;
        final int ROOM_HORIZONTAL = 7;
        final int VERTICAL_ROOM_WALL1 = 1;
        final int VERTICAL_ROOM_WALL2 = 2;
        final int HORIZONTAL_ROOM_WALL = 3;
        final int DOOR_SPOT = 3;
        final int KIOSK_SPOT = 34;

        int roomCount = 0;
        int computerCount = 0;
        // Counting the amount of rooms and computers the library has
        for (int i = 0; i < library.getResources().size(); i++) {
            if (library.getResources().get(i).getType() == ResourceType.ROOM) {
                roomCount++;
            } else if (library.getResources().get(i).getType() == ResourceType.COMPUTER) {
                computerCount++;
            }
        }

        // Adding the correct amount of rooms to the legend amount
        this.legend = new String[LEGEND_ESSENTIALS + roomCount];

        this.legend[0] = "ENTRANCES/EXITS: " + EXIT; // Index 0 of legend
        this.legend[1] = "MAIN DESK: " + MAIN_DESK; // Index 1 of legend
        this.legend[2] = "FANTASY SECTION: " + FANTASY; // Index 2 of legend
        this.legend[3] = "YOUNG ADULT SECTION: " + YOUNG_ADULT; // Index 3 of legend
        this.legend[4] = "ROMANCE SECTION: " + ROMANCE; // Index 4 of legend
        this.legend[5] = "SCIENCE FICTION SECTION: " + SCIENCE_FICTION; // Index 5 of legend
        this.legend[6] = "CHILDREN'S SECTION: " + CHILDREN; // Index 6 of legend
        this.legend[7] = "HISTORICAL FICTION SECTION: " + HISTORICAL_FICTION; // Index 7 of legend
        this.legend[8] = "NON-FICTION: " + NON_FICTION; // Index 8 of legend
        this.legend[9] = "MYSTERY SECTION: " + MYSTERY; // Index 9 of legend
        this.legend[10] = "HORROR SECTION: " + HORROR; // Index 10 of legend
        this.legend[11] = "COMPUTERS: " + COMPUTERS; // Index 11 of legend
        this.legend[12] = "DOOR: " + DOOR; // Index 12 of legend

        // Adding the correct amount of rooms to the legend
        if (roomCount > 0) {
            for (int i = 0; i < roomCount; i++) {
                this.legend[LEGEND_ESSENTIALS + i] = "ROOM " + (i + 1) + ": " + (i + 1);
            }
        }

        // Getting the dimensions for the display based on the amount of each resource
        final int ROOM_LENGTH = roomCount * ROOM_HORIZONTAL;
        final int COMPUTER_LENGTH = Math.round((float) computerCount / 2);
        final int HORIZONTAL = REGULAR_HORIZONTAL + (Math.max(ROOM_LENGTH, COMPUTER_LENGTH));
        this.display = new char[VERTICAL][HORIZONTAL];

        // Filling in map with white space
        for (int i = 0; i < this.display.length; i++) {
            for (int j = 0; j < this.display[0].length; j++) {
                this.display[i][j] = SPACE;
            }
        }

        // Walls
        for (int i = 1; i < VERTICAL; i++) {
            this.display[i][0] = VERTICAL_WALL;
            this.display[i][HORIZONTAL - 1] = VERTICAL_WALL;
        }

        for (int i = 0; i < HORIZONTAL; i++) {
            this.display[0][i] = HORIZONTAL_WALL;
            this.display[VERTICAL - 1][i] = HORIZONTAL_WALL;
        }
        
        // Exits
        this.display[0][HORIZONTAL_EXIT] = EXIT;
        this.display[VERTICAL_EXIT][HORIZONTAL - 1] = EXIT;

        // Kiosk
        this.display[1][KIOSK_SPOT] = KIOSK;
        
        // Main desk
        for (int i = 0; i < DESK_LENGTH; i++) {
            this.display[1][DESK_START + i] = MAIN_DESK;
        }
        
        // Bookshelves 
        for (int i = 0; i < BOOKSHELF; i++) {
            this.display[BOOKSHELF_ROW1][i + BOOKSHELF1_SPACE] = FANTASY;
            this.display[BOOKSHELF_ROW1][i + BOOKSHELF2_SPACE + BOOKSHELF] = YOUNG_ADULT;
            // There are 2 bookshelves and open space before this bookshelf
            this.display[BOOKSHELF_ROW1][i + BOOKSHELF3_SPACE + (2 * BOOKSHELF)] = ROMANCE;

            this.display[BOOKSHELF_ROW2][i + BOOKSHELF1_SPACE] = SCIENCE_FICTION;
            this.display[BOOKSHELF_ROW2][i + BOOKSHELF2_SPACE + BOOKSHELF] = CHILDREN;
            // There are 2 bookshelves and open space before this bookshelf
            this.display[BOOKSHELF_ROW2][i + BOOKSHELF3_SPACE + (2 * BOOKSHELF)] = HISTORICAL_FICTION;

            this.display[BOOKSHELF_ROW3][i + BOOKSHELF1_SPACE] = NON_FICTION;
            this.display[BOOKSHELF_ROW3][i + BOOKSHELF2_SPACE + BOOKSHELF] = MYSTERY;
            // There are 2 bookshelves and open space before this bookshelf
            this.display[BOOKSHELF_ROW3][i + BOOKSHELF3_SPACE + (2 * BOOKSHELF)] = HORROR;
        }
        
        // Computers
        if (computerCount > 0) {
            for (int i = 0; i < COMPUTER_LENGTH; i++) {
                this.display[COMPUTER_ROW_1][i + RESOURCE_START] = COMPUTERS;
                if (i + 1 == COMPUTER_LENGTH) {
                    // Checks if there's an even amount of computers
                    if (computerCount % 2 == 0) {
                        this.display[COMPUTER_ROW_2][i + RESOURCE_START] = COMPUTERS;
                    }
                } else {
                    this.display[COMPUTER_ROW_2][i + RESOURCE_START] = COMPUTERS;
                }
            }
        }

        // Rooms
        if (roomCount > 0) {
            int count = 1;

            for (int i = RESOURCE_START; i < ((roomCount * ROOM_HORIZONTAL) + RESOURCE_START); i++) {
                this.display[HORIZONTAL_ROOM_WALL][i] = HORIZONTAL_WALL;
            }

            for (int i = RESOURCE_START; i < HORIZONTAL; i += ROOM_HORIZONTAL) {
                this.display[VERTICAL_ROOM_WALL1][i] = VERTICAL_WALL;
                this.display[VERTICAL_ROOM_WALL2][i] = VERTICAL_WALL;

                if (count <= roomCount) {
                    this.display[HORIZONTAL_ROOM_WALL][i + DOOR_SPOT] = DOOR;

                    String roomNum = "" + count;
                    for (int j = 1; j <= roomNum.length(); j++) {
                        this.display[VERTICAL_ROOM_WALL2][i + j] = roomNum.charAt(j - 1);
                    }
                }
                count++;
            }
        }

        checkMap();
    }

    public char[][] getDisplay() {
        return this.display;
    }

    public String[] getLegend() {
        return this.legend;
    }
}
