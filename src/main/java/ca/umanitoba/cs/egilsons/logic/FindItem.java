package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.map.Coordinates;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import ca.umanitoba.cs.egilsons.domain.map.MapType;
import ca.umanitoba.cs.egilsons.logic.stack.LinkedListStack;

public class FindItem {
    private final Map map;

    public FindItem(Map map) {
        this.map = map;
    }

    public MapType[][] findItemPath(Coordinates itemCoordinates) {
        boolean found = false;
        MapType[][] path = map.getDisplay();
        Coordinates startCoordinates = map.getKiosk();
        boolean[][] visited = new boolean[map.getDisplay().length][map.getDisplay()[0].length];
        // boolean[][] stacked = new boolean[map.getDisplay().length][map.getDisplay()[0].length];
        fillGrid(visited);
        LinkedListStack<Coordinates> stack = new LinkedListStack<>();
        stack.push(startCoordinates);

        // Remove the parts of the path that include backtracking!!

        while (!found && !stack.isEmpty()) {
            Coordinates current = stack.pop();
            if (!visited[current.row()][current.column()]) {
                visited[current.row()][current.column()] = true;
                if (path[current.row()][current.column()] == null) {
                    path[current.row()][current.column()] = MapType.PATH;
                }
            } else {
                path[current.row()][current.column()] = null;
            }

            if (current.row() == itemCoordinates.row() && current.column() == itemCoordinates.column()) {
                found = true;
            } else {
                Coordinates up = new Coordinates(current.row() - 1, current.column());
                boolean upAdded = addToStack(up, itemCoordinates, visited, stack);

                Coordinates down = new Coordinates(current.row() + 1, current.column());
                boolean downAdded = addToStack(down, itemCoordinates, visited, stack);

                Coordinates right = new Coordinates(current.row(), current.column() + 1);
                boolean rightAdded = addToStack(right, itemCoordinates, visited, stack);

                Coordinates left = new Coordinates(current.row(), current.column() - 1);
                boolean leftAdded = addToStack(left, itemCoordinates, visited, stack);

                if (!upAdded && !downAdded && !rightAdded && !leftAdded) {
                    path[current.row()][current.column()] = null;
                }
            }
        }
        return path;
    }

    private void fillGrid(boolean[][] grid1) {
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[i].length; j++) {
                grid1[i][j] = false;
            }
        }
    }

    private boolean addToStack(Coordinates coordinates, Coordinates itemCoordinates, boolean[][] visited,
                            LinkedListStack<Coordinates> stack) {
        boolean added = false;
        if ((map.getDisplay()[coordinates.row()][coordinates.column()] == null
                && !visited[coordinates.row()][coordinates.column()]) || (coordinates.row() == itemCoordinates.row()
                && coordinates.column() == itemCoordinates.column())) {
            stack.push(coordinates);
            // stacked[coordinates.row()][coordinates.column()] = true;
            added = true;
        }
        return added;
    }
}
