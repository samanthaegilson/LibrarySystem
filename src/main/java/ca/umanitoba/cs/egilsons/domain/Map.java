package ca.umanitoba.cs.egilsons.domain;

import java.sql.Array;

public class Map {
    private String[][] display;
    private String[] legend;

    public Map(Library library) {
        final String HORIZONTAL_WALL = "-";
        final String VERTICAL_WALL = "|";
        final String EXIT = "E";
        final String MAIN_DESK = "D";
        final String FANTASY = "F";
        final String YOUNG_ADULT = "Y";
        final String ROMANCE = "R";
        final String SCIENCE_FICTION = "S";
        final String CHILDREN = "C";
        final String HISTORICAL_FICTION = "T";
        final String NON_FICTION = "N";
        final String MYSTERY = "M";
        final String HORROR = "H";
        final String COMPUTERS = "C";
        final int LIB_VERTICAL = 8;
        final int LIB_HORIZONTAL = 36;
        final int BOOKSHELF = 10;
        final int ROOM_VERTICAL = 1;
        final int ROOM_HORIZONTAL = 7;
        final int LEGEND_ESSENTIALS = 12;
        final int BOOKCASE_ROW_1 = 3;
        final int BOOKCASE_ROW_2 = 5;
        final int BOOKCASE_ROW_3 = 7;
        final int HORIZONTAL_EXIT = 9;
        final int VERTICAL_EXIT = 5;
        final int DESK_POSITION = 11;
        final int COMPUTER_ROW_1 = 6;
        final int COMPUTER_ROW_2 = 8;
        final int RESOURCE_START = 34;

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

        this.legend[0] = "ENTRANCES/EXITS: " + EXIT;
        this.legend[1] = "MAIN DESK: " + MAIN_DESK;
        this.legend[2] = "FANTASY SECTION: " + FANTASY;
        this.legend[3] = "YOUNG ADULT SECTION: " + YOUNG_ADULT;
        this.legend[4] = "ROMANCE SECTION: " + ROMANCE;
        this.legend[5] = "SCIENCE FICTION SECTION: " + SCIENCE_FICTION;
        this.legend[6] = "CHILDREN'S SECTION: " + CHILDREN;
        this.legend[7] = "HISTORICAL FICTION SECTION: " + HISTORICAL_FICTION;
        this.legend[8] = "NON-FICTION: " + NON_FICTION;
        this.legend[9] = "MYSTERY SECTION: " + MYSTERY;
        this.legend[10] = "HORROR SECTION: " + HORROR;
        this.legend[11] = "COMPUTERS: " + COMPUTERS;

        // Adding the correct amount of rooms to the legend
        for (int i = 0; i < roomCount; i++) {
            this.legend[LEGEND_ESSENTIALS + 1 + i] = "ROOM " + i + ": " + i;
        }

        final int ROOM_LENGTH = roomCount * ROOM_HORIZONTAL;
        final int COMPUTER_LENGTH = Math.round(computerCount / 2);
        final int HORIZONTAL = LIB_HORIZONTAL + (Math.max(ROOM_LENGTH, COMPUTER_LENGTH));
        this.display = new String[HORIZONTAL][LIB_VERTICAL];

        // Walls
        for (int i = 0; i < HORIZONTAL; i++) {
            this.display[0][i] = HORIZONTAL_WALL;
            this.display[LIB_VERTICAL - 1][i] = HORIZONTAL_WALL;
        }

        for (int i = 0; i < LIB_VERTICAL; i++) {
            this.display[i + 1][0] = VERTICAL_WALL;
            this.display[i + 1][HORIZONTAL - 1] = VERTICAL_WALL;
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
        for (int i = 0; i < COMPUTER_LENGTH; i++) {
            this.display[COMPUTER_ROW_1][i + RESOURCE_START] = COMPUTERS;
            this.display[COMPUTER_ROW_2][i + RESOURCE_START] = COMPUTERS;
        }

        // Rooms
        for (int i = RESOURCE_START; i < HORIZONTAL; i += ROOM_HORIZONTAL) {
            this.display[1][i] = VERTICAL_WALL;
            this.display[2][i] = VERTICAL_WALL;
        }
        for (int i = RESOURCE_START; i < HORIZONTAL; i++) {
            this.display[3][i] = HORIZONTAL_WALL;
        }



        String test = """
                --------E---------------------------------
                |               D   |
                |                                  |    |
                | mmmmmmmmmm mmmmmmmmmm mmmmmmmmmm -------
                |                                  
                | mmmmmmmmmm mmmmmmmmmm mmmmmmmmmm 
                |                                   cccccccc 
                | mmmmmmmmmm mmmmmmmmmm mmmmmmmmmm
                |                                   cccccccc
                -----------------------------------
                """;
    }

    public String[][] getDisplay() {
        return this.display;
    }

    public String[] getLegend() {
        return this.legend;
    }
}
