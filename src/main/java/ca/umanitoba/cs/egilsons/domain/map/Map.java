package ca.umanitoba.cs.egilsons.domain.map;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.resource.Computer;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import com.google.common.base.Preconditions;

/**
 * A map. There is one map for every {@link Library} that shows where everything is.
 */
public class Map {
    private MapType[][] display;

    /**
     * Invariant properties for Map
     */
    private void checkMap() {
        Preconditions.checkNotNull(display, "Display should never be null.");
    }

    /**
     * Constructor of a map. Receive input for the library
     *
     * @param library the library the map is displaying
     */
    public Map(Library library) {
        // Constants for spacing
        final int VERTICAL = 10;
        final int REGULAR_HORIZONTAL = 36;
        final int BOOKSHELF = 10;
        final int BOOKSHELF_ROW1 = 3;
        final int BOOKSHELF_ROW2 = 5;
        final int BOOKSHELF_ROW3 = 7;
        final int BOOKSHELF2_SPACE = 3;
        final int BOOKSHELF3_SPACE = 4;
        final int BOOKSHELF1_START = 2;
        final int BOOKSHELF2_START = BOOKSHELF2_SPACE + BOOKSHELF;
        final int BOOKSHELF3_START = BOOKSHELF3_SPACE + (2 * BOOKSHELF); // There are 2 bookshelves and open space
                                                                         // before this bookshelf
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

        int roomCount = 0;
        int computerCount = 0;
        // Counting the amount of rooms and computers the library has
        for (int i = 0; i < library.getResources().size(); i++) {
            if (library.getResources().get(i) instanceof Room) {
                roomCount++;
            } else if (library.getResources().get(i) instanceof Computer) {
                computerCount++;
            }
        }

        // Getting the dimensions for the display based on the amount of each resource
        final int ROOM_LENGTH = roomCount* ROOM_HORIZONTAL;
        final int COMPUTER_LENGTH = Math.round((float) computerCount / 2);
        final int HORIZONTAL = REGULAR_HORIZONTAL + (Math.max(ROOM_LENGTH, COMPUTER_LENGTH));
        this.display = new MapType[VERTICAL][HORIZONTAL];

        // Walls
        for (int i = 1; i < VERTICAL; i++) {
            this.display[i][0] = MapType.WALL;
            this.display[i][HORIZONTAL - 1] = MapType.WALL;
        }

        for (int i = 0; i < HORIZONTAL; i++) {
            this.display[0][i] = MapType.WALL;
            this.display[VERTICAL - 1][i] = MapType.WALL;
        }
        
        // Exits
        this.display[0][HORIZONTAL_EXIT] = MapType.EXIT;
        this.display[VERTICAL_EXIT][HORIZONTAL - 1] = MapType.EXIT;
        
        // Main desk
        for (int i = 0; i < DESK_LENGTH; i++) {
            this.display[1][DESK_START + i] = MapType.DESK;
        }

        // Bookshelves
        for (int i = 0; i < BOOKSHELF; i++) {
            // First row of bookshelves
            this.display[BOOKSHELF_ROW1][i + BOOKSHELF1_START] = MapType.FANTASY_SECTION;
            this.display[BOOKSHELF_ROW1][i + BOOKSHELF2_START] = MapType.YOUNG_ADULT_SECTION;
            this.display[BOOKSHELF_ROW1][i + BOOKSHELF3_START] = MapType.ROMANCE_SECTION;

            // Second row of bookshelves
            this.display[BOOKSHELF_ROW2][i + BOOKSHELF1_START] = MapType.SCIENCE_FICTION_SECTION;
            this.display[BOOKSHELF_ROW2][i + BOOKSHELF2_START] = MapType.CHILDREN_SECTION;
            this.display[BOOKSHELF_ROW2][i + BOOKSHELF3_START] = MapType.HISTORICAL_FICTION_SECTION;

            // Third row of bookshelves
            this.display[BOOKSHELF_ROW3][i + BOOKSHELF1_START] = MapType.NON_FICTION_SECTION;
            this.display[BOOKSHELF_ROW3][i + BOOKSHELF2_START] = MapType.MYSTERY_SECTION;
            this.display[BOOKSHELF_ROW3][i + BOOKSHELF3_START] = MapType.HORROR_SECTION;
        }
        
        // Computers
        for (int i = 0; i < COMPUTER_LENGTH; i++) {
            this.display[COMPUTER_ROW_1][i + RESOURCE_START] = MapType.COMPUTER;
        }
        for (int i = 0; i < (computerCount - COMPUTER_LENGTH); i++) {
            this.display[COMPUTER_ROW_2][i + RESOURCE_START] = MapType.COMPUTER;
        }

        // Rooms
        if (roomCount > 0) {
            int count = 0;

            for (int i = RESOURCE_START; i < ((roomCount * ROOM_HORIZONTAL) + RESOURCE_START); i++) {
                this.display[HORIZONTAL_ROOM_WALL][i] = MapType.WALL;
            }

            for (int i = RESOURCE_START; i < HORIZONTAL; i += ROOM_HORIZONTAL) {
                this.display[VERTICAL_ROOM_WALL1][i] = MapType.WALL;
                this.display[VERTICAL_ROOM_WALL2][i] = MapType.WALL;

                if (count < roomCount) {
                    this.display[HORIZONTAL_ROOM_WALL][i + DOOR_SPOT] = MapType.ROOM_DOOR;
                }
                count++;
            }
        }

        checkMap();
    }

    public MapType[][] getDisplay() {
        return this.display;
    }
}
