package ca.umanitoba.cs.egilsons.domain.map;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.domain.resource.Computer;
import ca.umanitoba.cs.egilsons.domain.resource.Resource;
import ca.umanitoba.cs.egilsons.domain.resource.Room;
import com.google.common.base.Preconditions;

/**
 * A map. There is one map for every {@link Library} that shows where everything is.
 */
public class Map {
    private MapType[][] display;
    private Coordinates kiosk;

    /**
     * Invariant properties for Map
     */
    private void checkMap() {
        Preconditions.checkNotNull(display, "Display should never be null.");
        Preconditions.checkNotNull(kiosk, "The kiosk coordinates should never be null.");
    }

    /**
     * Picks a bookshelf for the media based on the first letter of the title
     *
     * @param media the media to find a bookshelf for
     * @return the bookshelf number
     */
    private static int pickBookshelfNum(Media media) {
        final int FIRST_LETTER = media.getTitle().toUpperCase().charAt(0);
        // Constants for the bookshelf separation
        final int A = 65;
        final int D = 68;
        final int G = 71;
        final int J = 74;
        final int M = 77;
        final int P = 80;
        final int S = 83;
        final int V = 86;
        final int Y = 89;
        final int Z = 90;
        int bookshelf;

        if (FIRST_LETTER >= A && FIRST_LETTER < D) {
            bookshelf = 0;
        } else if (FIRST_LETTER >= D && FIRST_LETTER < G) {
            bookshelf = 1;
        } else if (FIRST_LETTER >= G && FIRST_LETTER < J) {
            bookshelf = 2;
        } else if (FIRST_LETTER >= J && FIRST_LETTER < M) {
            bookshelf = 3;
        } else if (FIRST_LETTER >= M && FIRST_LETTER < P) {
            bookshelf = 4;
        } else if (FIRST_LETTER >= P && FIRST_LETTER < S) {
            bookshelf = 5;
        } else if (FIRST_LETTER >= S && FIRST_LETTER < V) {
            bookshelf = 6;
        } else if (FIRST_LETTER >= V && FIRST_LETTER < Y) {
            bookshelf = 7;
        } else if (FIRST_LETTER >= Y && FIRST_LETTER < Z) {
            bookshelf = 8;
        } else {
            bookshelf = 9;
        }

        return bookshelf;
    }

    public Map(MapType[][] display) {
        this.display = display;
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
        final int KIOSK_SPOT = 28;

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

        // Kiosk
        this.display[1][KIOSK_SPOT] = MapType.KIOSK;
        this.kiosk = new Coordinates(1, KIOSK_SPOT);
        
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

    public Coordinates getKiosk() {
        return this.kiosk;
    }

    /**
     * Sets the coordinate of a media
     *
     * @param media the media to find the coordinate of
     * @return the coordinates of the media
     */
    public static Coordinates setMediaCoordinates(Media media) {
        final int BOOKSHELF1_ROW = 3;
        final int BOOKSHELF2_ROW = 5;
        final int BOOKSHELF3_ROW = 7;
        final int FANTASY_START = 2;
        final int YOUNG_ADULT_START = 13;
        final int ROMANCE_START = 24;
        final int SCIENCE_FICTION_START = 2;
        final int CHILDREN_START = 13;
        final int HISTORICAL_FICTION_START = 24;
        final int NON_FICTION_START = 2;
        final int MYSTERY_START = 13;
        final int HORROR_START = 24;
        Coordinates coordinates = null;
        int bookshelfNum = pickBookshelfNum(media);

        // Sets the coordinates by media category
        if (media.getCategory() == MediaCategory.FANTASY) {
            coordinates = new Coordinates(BOOKSHELF1_ROW, FANTASY_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.YOUNG_ADULT) {
            coordinates = new Coordinates(BOOKSHELF1_ROW, YOUNG_ADULT_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.ROMANCE) {
            coordinates = new Coordinates(BOOKSHELF1_ROW, ROMANCE_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.SCIENCE_FICTION) {
            coordinates = new Coordinates(BOOKSHELF2_ROW, SCIENCE_FICTION_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.CHILDREN) {
            coordinates = new Coordinates(BOOKSHELF2_ROW, CHILDREN_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.HISTORICAL_FICTION) {
            coordinates = new Coordinates(BOOKSHELF2_ROW, HISTORICAL_FICTION_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.NON_FICTION) {
            coordinates = new Coordinates(BOOKSHELF3_ROW, NON_FICTION_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.MYSTERY) {
            coordinates = new Coordinates(BOOKSHELF3_ROW, MYSTERY_START + bookshelfNum);
        } else if (media.getCategory() == MediaCategory.HORROR) {
            coordinates = new Coordinates(BOOKSHELF3_ROW, HORROR_START + bookshelfNum);
        }

        return coordinates;
    }

    /**
     * Sets the coordinate of a resource
     *
     * @param resource the resource to find the coordinate of
     * @return the coordinates of the resource
     */
    public static Coordinates setResourceCoordinates(Resource resource) {
        final int ROOM_START = 38;
        final int ROOM_SPACE = 7;
        final int ROOM_ROW = 3;
        final int COMPUTER_ROW_1 = 6;
        final int COMPUTER_ROW_2 = 8;
        final int COMPUTER_START = 35;
        final int RESOURCE_NUM = resource.getNumber();
        Coordinates coordinates;

        if (resource instanceof Room) {
            coordinates = new Coordinates(ROOM_ROW, ROOM_START + ((RESOURCE_NUM - 1) * ROOM_SPACE));
        } else {
            if (RESOURCE_NUM % 2 == 1) {
                // First row of computers
                coordinates = new Coordinates(COMPUTER_ROW_1, COMPUTER_START + (RESOURCE_NUM - 1));
            } else {
                // Second row of computers
                coordinates = new Coordinates(COMPUTER_ROW_2, COMPUTER_START + (RESOURCE_NUM/2 - 1));
            }
        }

        return coordinates;
    }
}
