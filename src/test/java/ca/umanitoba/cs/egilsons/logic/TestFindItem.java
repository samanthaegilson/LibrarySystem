package ca.umanitoba.cs.egilsons.logic;

import ca.umanitoba.cs.egilsons.domain.Library;
import ca.umanitoba.cs.egilsons.domain.map.MapType;
import ca.umanitoba.cs.egilsons.domain.media.Book;
import ca.umanitoba.cs.egilsons.domain.map.Map;
import ca.umanitoba.cs.egilsons.domain.media.Media;
import ca.umanitoba.cs.egilsons.domain.media.MediaCategory;
import ca.umanitoba.cs.egilsons.tests.TestResults;

public class TestFindItem {
    private int successes = 0;
    private int failures = 0;

    public TestResults runTests() {
        testFindPath();

        return new TestResults(successes, failures);
    }

    public void testFindPath() {
        try {
            Library library = new Library.LibraryBuilder().name("Westwood").build();
            Media media = new Book("Test", "Test", 100, MediaCategory.SCIENCE_FICTION);
            FindItem findItem = new FindItem(library);

            Map path = findItem.findItemPath(media.getCoordinates());
            int row = media.getCoordinates().row();
            int column = media.getCoordinates().column();
            if (path.getDisplay()[row][column] == MapType.DESTINATION) {
                pass("Map path is successfully found.");
            } else {
                fail("Map destination is not the destination coordinates.");
            }
        } catch (Exception e) {
            fail("Some exception was thrown.");
            e.printStackTrace();
        }
    }

    private void pass(String message) {
        successes++;
        System.out.println("PASS: " + message);
    }

    private void fail(String message) {
        failures++;
        System.out.println("FAIL: " + message);
    }
}
