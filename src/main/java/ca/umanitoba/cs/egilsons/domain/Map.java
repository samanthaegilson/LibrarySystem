package ca.umanitoba.cs.egilsons.domain;

import ca.umanitoba.cs.egilsons.domain.resource.ResourceType;

/**
 * A map. There is one map for every {@link Library} that shows where everything is.
 */
public class Map {
    private char[][] display;
    private String[] legend;

    public Map(Library library) {
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
        final int LIB_VERTICAL = 8;
        final int LIB_HORIZONTAL = 36;
        final int BOOKSHELF = 10;
        final int ROOM_HORIZONTAL = 7;
        final int LEGEND_ESSENTIALS = 12;
        final int BOOKCASE_ROW_1 = 2;
        final int BOOKCASE_ROW_2 = 4;
        final int BOOKCASE_ROW_3 = 6;
        final int HORIZONTAL_EXIT = 9;
        final int VERTICAL_EXIT = 5;
        final int DESK_POSITION = 13;
        final int COMPUTER_ROW_1 = 5;
        final int COMPUTER_ROW_2 = 7;
        final int RESOURCE_START = 35;

        int roomCount = 0;
        int computerCount = 0;

        for (int i = 0; i < library.getResources().size(); i++) {
            if (library.getResources().get(i).getType() == ResourceType.ROOM) {
                roomCount++;
            } else if (library.getResources().get(i).getType() == ResourceType.COMPUTER) {
                computerCount++;
            }
        }

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

        // Adding the correct amount of rooms to the legend
        for (int i = 0; i < roomCount; i++) {
            this.legend[LEGEND_ESSENTIALS + i] = "ROOM " + i + ": " + i;
        }

        final int ROOM_LENGTH = roomCount * ROOM_HORIZONTAL;
        final int COMPUTER_LENGTH = Math.round((float) computerCount / 2);
        final int HORIZONTAL = LIB_HORIZONTAL + (Math.max(ROOM_LENGTH, COMPUTER_LENGTH));
        this.display = new char[LIB_VERTICAL][HORIZONTAL];

        for (int i = 0; i < this.display.length; i++) {
            for (int j = 0; j < this.display[0].length; j++) {
                this.display[i][j] = ' ';
            }
        }

        // Walls
        for (int i = 0; i < HORIZONTAL; i++) {
            this.display[0][i] = HORIZONTAL_WALL;
            this.display[LIB_VERTICAL - 1][i] = HORIZONTAL_WALL;
        }

        for (int i = 1; i < LIB_VERTICAL; i++) {
            this.display[i][0] = VERTICAL_WALL;
            this.display[i][HORIZONTAL - 1] = VERTICAL_WALL;
        }
        
        // Exits
        this.display[0][HORIZONTAL_EXIT] = EXIT;
        this.display[VERTICAL_EXIT][HORIZONTAL - 1] = EXIT;
        
        // Main desk
        this.display[1][DESK_POSITION] = MAIN_DESK;
        
        // Bookshelves 
        for (int i = 0; i < BOOKSHELF; i++) {
            this.display[BOOKCASE_ROW_1][i + 2] = FANTASY;
            this.display[BOOKCASE_ROW_1][i + 3 + BOOKSHELF] = YOUNG_ADULT;
            this.display[BOOKCASE_ROW_1][i + 4 + (2 * BOOKSHELF)] = ROMANCE;

            this.display[BOOKCASE_ROW_2][i + 2] = SCIENCE_FICTION;
            this.display[BOOKCASE_ROW_2][i + 3 + BOOKSHELF] = CHILDREN;
            this.display[BOOKCASE_ROW_2][i + 4 + (2 * BOOKSHELF)] = HISTORICAL_FICTION;

            this.display[BOOKCASE_ROW_3][i + 2] = NON_FICTION;
            this.display[BOOKCASE_ROW_3][i + 3 + BOOKSHELF] = MYSTERY;
            this.display[BOOKCASE_ROW_3][i + 4 + (2 * BOOKSHELF)] = HORROR;
        }
        
        // Computers
        if (computerCount > 0) {
            for (int i = 0; i < COMPUTER_LENGTH; i++) {
                this.display[COMPUTER_ROW_1][i + RESOURCE_START] = COMPUTERS;
                if (computerCount % 2 == 0) {
                    this.display[COMPUTER_ROW_2][i + RESOURCE_START] = COMPUTERS;
                }
            }
        }

        // Rooms
        int roomIndex = 0;
        if (roomCount > 0) {
            for (int i = RESOURCE_START; i < HORIZONTAL; i += ROOM_HORIZONTAL) {
                this.display[1][i] = VERTICAL_WALL;
                this.display[2][i] = VERTICAL_WALL;
            }
            for (int i = RESOURCE_START; i < HORIZONTAL - 1; i++) {
                this.display[3][i] = HORIZONTAL_WALL;
            }
        }



        String test = """
                --------E---------------------------------
                |               D   |
                |                                  | 1  |
                | mmmmmmmmmm mmmmmmmmmm mmmmmmmmmm --o----
                |                                  
                | mmmmmmmmmm mmmmmmmmmm mmmmmmmmmm 
                |                                   cccccccc 
                | mmmmmmmmmm mmmmmmmmmm mmmmmmmmmm
                |                                   cccccccc
                -----------------------------------
                """;
    }

    public char[][] getDisplay() {
        return this.display;
    }

    public String[] getLegend() {
        return this.legend;
    }
}
