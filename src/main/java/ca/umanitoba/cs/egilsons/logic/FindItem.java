package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.map.Coordinates;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import ca.umanitoba.cs.egilsons.domain.map.MapType;
import ca.umanitoba.cs.egilsons.domain.stack.LinkedListStack;
import com.google.common.base.Preconditions;

/**
 * Logic for finding an item on a {@link Map}.
 */
public class FindItem {
    private final Library library;

    /**
     * Checks that FindItem is in a valid state
     */
    private void checkFindItem() {
        Preconditions.checkNotNull(library, "Library should not be null");
    }

    /**
     * A constructor for FindItem
     *
     * @param library the library with the map
     */
    public FindItem(Library library) {
        this.library = library;
        checkFindItem();
    }

    /**
     * Finds the path from the library kiosk to an item
     *
     * @param itemCoordinates the coordinates of the desired item
     * @return a map marked with the path to the item
     */
    public Map findItemPath(Coordinates itemCoordinates) {
        checkFindItem();
        Preconditions.checkNotNull(itemCoordinates, "Item coordinates should not be null");

        boolean found = false;
        Map path = new Map(this.library);
        Coordinates startCoordinates = path.getKiosk();
        boolean[][] visited = new boolean[path.getDisplay().length][path.getDisplay()[0].length];
        fillGrid(visited);
        LinkedListStack<Coordinates> stack = new LinkedListStack<>();
        stack.push(startCoordinates);

        while (!found && !stack.isEmpty()) {
            Coordinates current = stack.pop();
            visited[current.row()][current.column()] = true;
            // Marking the path
            if (path.getDisplay()[current.row()][current.column()] == null) {
                path.getDisplay()[current.row()][current.column()] = MapType.PATH;
            }

            if (current.row() == itemCoordinates.row() && current.column() == itemCoordinates.column()) {
                // Item is found
                found = true;
                path.getDisplay()[current.row()][current.column()] = MapType.DESTINATION;
            } else {
                // Add all directions to stack
                Coordinates up = new Coordinates(current.row() - 1, current.column());
                addToStack(up, itemCoordinates, visited, stack, path);

                Coordinates down = new Coordinates(current.row() + 1, current.column());
                addToStack(down, itemCoordinates, visited, stack, path);

                Coordinates right = new Coordinates(current.row(), current.column() + 1);
                addToStack(right, itemCoordinates, visited, stack, path);

                Coordinates left = new Coordinates(current.row(), current.column() - 1);
                addToStack(left, itemCoordinates, visited, stack, path);
            }
        }

        checkFindItem();
        return path;
    }

    /**
     * Fills a boolean grid with the default value of false
     *
     * @param grid the grid to fill
     */
    private void fillGrid(boolean[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = false;
            }
        }
    }

    /**
     * Determines if coordinates can be added to the stack
     *
     * @param coordinates the coordinates to add
     * @param itemCoordinates the coordinates of the item to find
     * @param visited a grid of visited coordinates
     * @param stack the stack of potential moves
     * @param path the map of the current path
     */
    private void addToStack(Coordinates coordinates, Coordinates itemCoordinates, boolean[][] visited,
                            LinkedListStack<Coordinates> stack, Map path) {
        if ((path.getDisplay()[coordinates.row()][coordinates.column()] == null
                && !visited[coordinates.row()][coordinates.column()]) || (coordinates.row() == itemCoordinates.row()
                && coordinates.column() == itemCoordinates.column())) {
            stack.push(coordinates);
        }
    }
}
